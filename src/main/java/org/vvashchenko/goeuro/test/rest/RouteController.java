package org.vvashchenko.goeuro.test.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vvashchenko.goeuro.test.responses.DirectRouteResponse;
import org.vvashchenko.goeuro.test.service.interfaces.RouteService;

@RestController
@RequestMapping("/api")
public class RouteController {

    @Autowired
    @Qualifier("RouteServiceImpl")
    private RouteService busRouteService;

    @RequestMapping(value = "/direct", method = RequestMethod.GET)
    public ResponseEntity<DirectRouteResponse> getDirectRouteResponse(
            @RequestParam("dep_sid") int departureSid,
            @RequestParam("arr_sid") int arrivalSid) {

        boolean directBusRoute = busRouteService.isDirectRoute(departureSid, arrivalSid);
        DirectRouteResponse response = new DirectRouteResponse(departureSid, arrivalSid, directBusRoute);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
