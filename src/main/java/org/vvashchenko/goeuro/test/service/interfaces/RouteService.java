package org.vvashchenko.goeuro.test.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface RouteService {

    boolean isDirectRoute(int departureStationId, int arrivalStationId);
}
