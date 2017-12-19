package org.vvashchenko.goeuro.test.store;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.ResourceUtils;
import org.vvashchenko.goeuro.test.TestConfig;
import org.vvashchenko.goeuro.test.model.Station;
import org.vvashchenko.goeuro.test.store.interfaces.RoutesAccessor;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class InMemoryInitializerTest {

    private static final String FILE_NAME = "routes.txt";
    private static final String TEST_ROUTE_IDS = "3 0 7 8";

    private File routeFile;
    private Path pathToFile;

    @Autowired
    private RoutesAccessor routesAccessor;

    @Before
    public void init() throws IOException {
        try {
            routeFile = new File(ResourceUtils.getURL(FILE_NAME).getFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No route file by path " + FILE_NAME);
        }

        if (!routeFile.exists()) {
            throw new RuntimeException("No route file by path " + FILE_NAME);
        }

        pathToFile = Paths.get(routeFile.getPath());
    }

    @Test
    public void testRouteFileExists() {
        assertTrue("Route file is absent at " + FILE_NAME, routeFile.exists());
    }

    @Test
    public void testRouteContainsEnoughIds() throws IOException {
        Set<Integer> routeIds = new HashSet<>();
        HashMap<Integer, Collection<Station>> stationIds = new HashMap<>();
        Files.lines(pathToFile).forEach(route -> {
            String[] stations = route.split(" ");
            if (stations.length > 2) {
                int routeId = Integer.valueOf(stations[0]);
                routeIds.add(routeId);
                Collection<Station> stationsInFile = new HashSet<>();
                for (int i = 1; i < stations.length; i++) {
                    Station station = new Station(Integer.valueOf(stations[i]));
                    stationsInFile.add(station);
                }
                stationIds.put(routeId, stationsInFile);
            }
        });
        for (Integer routeId : routeIds) {
            Collection<Station> expectedStations = stationIds.get(routeId);
            Collection<Station> actualStations = routesAccessor.getRouteById(routeId).getStations();
            assertTrue(actualStations.containsAll(expectedStations));
        }
    }

    @Test
    public void testNewDataAppearsInStores() throws IOException, InterruptedException {
        testRouteContainsEnoughIds();

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, true)))) {
            out.print(System.getProperty("line.separator"));
            out.println(TEST_ROUTE_IDS);
        }

        Thread.sleep(11 * 1000);

        testRouteContainsEnoughIds();
    }
}
