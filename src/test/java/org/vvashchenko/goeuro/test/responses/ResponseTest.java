package org.vvashchenko.goeuro.test.responses;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.vvashchenko.goeuro.test.TestConfig;
import org.vvashchenko.goeuro.test.rest.RouteController;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class ResponseTest {

    @Autowired
    private RouteController routeController;

    @Test
    public void testResponseContainsRequestedStations() {
        ResponseEntity<DirectRouteResponse> response = routeController.getDirectRouteResponse(3, 6);
        assertEquals(response.getBody().getDepSid(), 3);
        assertEquals(response.getBody().getArrSid(), 6);
    }

}
