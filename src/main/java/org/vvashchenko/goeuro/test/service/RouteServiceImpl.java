package org.vvashchenko.goeuro.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vvashchenko.goeuro.test.routing.interfaces.RoutesDAO;
import org.vvashchenko.goeuro.test.routing.interfaces.StationDAO;
import org.vvashchenko.goeuro.test.model.Route;
import org.vvashchenko.goeuro.test.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vvashchenko.goeuro.test.service.interfaces.RouteService;

import java.util.Set;

@Slf4j
@Service
@Qualifier("RouteServiceImpl")
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RoutesDAO routesDAO;

    @Autowired
    private StationDAO stationDAO;

    @Override
    public boolean isDirectRoute(int departureStationId, int arrivalStationId) {
        Station departureStation = stationDAO.getStationById(departureStationId);
        Station arrivalStation = stationDAO.getStationById(arrivalStationId);
        if (arrivalStation == null || departureStation == null) {
            log.info("No route between stations.");
            log.info("Requested routes are: departure station = {} and arrival station = {}",
                    departureStationId, arrivalStationId );
            return false;
        }
        Set<Route> routesByDepartureStation = routesDAO.getRoutesByStation(departureStation);
        Set<Route> routesByArrivalStation = routesDAO.getRoutesByStation(arrivalStation);
        return checkDirectRoute(routesByDepartureStation, routesByArrivalStation);
    }


    private boolean checkDirectRoute(Set<Route> routesByDepartureStation, Set<Route> routesByArrivalStation) {
        for (Route route : routesByArrivalStation) {
            if (routesByDepartureStation.contains(route)) {
                return true;
            }
        }
        return false;
    }
}
