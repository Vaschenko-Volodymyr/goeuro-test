package org.vvashchenko.goeuro.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.vvashchenko.goeuro.test.model.Route;
import org.vvashchenko.goeuro.test.model.Station;
import org.vvashchenko.goeuro.test.store.interfaces.RoutesAccessor;
import org.vvashchenko.goeuro.test.store.interfaces.StationsAccessor;
import org.vvashchenko.goeuro.test.utils.interfaces.StoreInitializer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Qualifier("InMemoryStoreInitializer")
public class InMemoryStoreInitializer implements StoreInitializer {

    private static final String FILE_NAME = "routes.txt";
    private static final long UPDATE_RATE_MILLIS = 10 * 1000; // each 10 sec

    private static final int ROUTE_ID = 0;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final RouteFileUpdateChecker routeFileUpdateChecker = new RouteFileUpdateChecker();

    private File routeFile;
    private Path pathToFile;

    @Autowired
    private RoutesAccessor routesAccessor;

    @Autowired
    private StationsAccessor stationsAccessor;

    @PostConstruct
    private void initRoutes() {
        loadRouteFile();
        loadRoutesFromFile();
        startRouteFileUpdateChecker();
    }

    private void loadRouteFile() {

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

    @PreDestroy
    private void cleanUp() {
        scheduler.shutdown();
    }

    private void startRouteFileUpdateChecker() {
        log.info("Started update checker");
        scheduler.scheduleAtFixedRate(routeFileUpdateChecker, 10, 10, TimeUnit.SECONDS);
    }

    private long getLastModifiedTime() {
        return routeFile.lastModified();
    }

    private synchronized void loadRoutesFromFile() {
        log.info("Loading routes from external resource");

        startTransaction();
        clear();

        try {
            Files.lines(pathToFile).forEach(route -> {
                String[] stations = route.split(" ");
                if (route.length() > 2) {
                    saveToMemory(stations);
                }
            });
            log.info("Successfully loaded.");
        } catch (IOException e) {
            log.error("Cannot load routes.", e);
        }

        endTransaction();
    }

    private void saveToMemory(String[] stations) {
        Set<Station> routeStationsList = new HashSet<>(stations.length);
        int routeId = Integer.valueOf(stations[ROUTE_ID]);
        for (int i = 1; i < stations.length; i++) { // save stations to collection
            int stationId = Integer.valueOf(stations[i]);
            Station station = new Station(stationId);
            station.getRoutesIds().add(routeId);
            routeStationsList.add(station);
            saveStation(station);
        }
        saveRoute(routeId, routeStationsList);
    }

    private void saveStation(Station station) {
        stationsAccessor.add(station);
    }

    private void saveRoute(int id, Set<Station> routeStations) {
        Route route = new Route(id);
        route.setStations(routeStations);
        routesAccessor.addRoute(route);
    }

    private class RouteFileUpdateChecker implements Runnable {

        @Override
        public void run() {
            long lastModified = getLastModifiedTime();
            if (lastModified > System.currentTimeMillis() - UPDATE_RATE_MILLIS) {
                log.info("Route file was modified! Loading new routes");
                loadRoutesFromFile();
            }
        }
    }

    private void startTransaction() {
        routesAccessor.startTransaction();
        stationsAccessor.startTransaction();
    }

    private void endTransaction() {
        routesAccessor.endTransaction();
        stationsAccessor.endTransaction();
    }

    private void clear() {
        routesAccessor.clear();
        stationsAccessor.clear();
    }
}
