package org.vvashchenko.goeuro.test.requests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.vvashchenko.goeuro.test.TestConfig;
import org.vvashchenko.goeuro.test.responses.DirectRouteResponse;
import org.vvashchenko.goeuro.test.rest.RouteController;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class RequestsTest {

    @Autowired
    private RouteController routeController;

    @Test
    public void testStatusCode() {
        ResponseEntity<DirectRouteResponse> response = routeController.getDirectRouteResponse(3, 6);
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void testPositiveRouting() {
        ResponseEntity<DirectRouteResponse> response = routeController.getDirectRouteResponse(3, 6);
        assertTrue(response.getBody().isDirectBusRoute());
    }

    @Test
    public void testNegativeRouting() {
        ResponseEntity<DirectRouteResponse> response = routeController.getDirectRouteResponse(-3, -6);
        assertFalse(response.getBody().isDirectBusRoute());
    }

}
