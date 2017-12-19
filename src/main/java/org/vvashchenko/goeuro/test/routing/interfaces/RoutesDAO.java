package org.vvashchenko.goeuro.test.routing.interfaces;

import org.vvashchenko.goeuro.test.model.Route;
import org.vvashchenko.goeuro.test.model.Station;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoutesDAO {

    Set<Route> getRoutesByStation(Station station);

}
