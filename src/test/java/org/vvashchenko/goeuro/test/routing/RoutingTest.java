package org.vvashchenko.goeuro.test.routing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.vvashchenko.goeuro.test.TestConfig;
import org.vvashchenko.goeuro.test.service.interfaces.RouteService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class RoutingTest {

    @Autowired
    private RouteService routeService;

    @Test
    public void testExistingRoute() {
        boolean routeExists = routeService.isDirectRoute(3, 6);
        assertTrue("Route is absent!", routeExists);
    }

    @Test
    public void testAbsenceRoute() {
        boolean routeExists = routeService.isDirectRoute(30, 60);
        assertFalse("Route exists but shouldn't!", routeExists);
    }

    @Test
    public void testRoutingWithNegativeIds() {
        boolean routeExists = routeService.isDirectRoute(-30, -60);
        assertFalse("Route exists but shouldn't!", routeExists);
    }
}
