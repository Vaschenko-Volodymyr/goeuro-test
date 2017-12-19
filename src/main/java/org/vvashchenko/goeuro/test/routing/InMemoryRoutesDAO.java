package org.vvashchenko.goeuro.test.routing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.vvashchenko.goeuro.test.model.Station;
import org.vvashchenko.goeuro.test.model.Route;
import org.vvashchenko.goeuro.test.routing.interfaces.RoutesDAO;
import org.vvashchenko.goeuro.test.store.interfaces.RoutesAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
@Qualifier("inMemoryRoutesDAO")
public class InMemoryRoutesDAO implements RoutesDAO {

    @Autowired
    private RoutesAccessor routesAccessor;

    @Override
    public Set<Route> getRoutesByStation(Station station) {
        Set<Route> routes = new HashSet<>(station.getRoutesIds().size());
        station.getRoutesIds().forEach(routeId -> routes.add(routesAccessor.getRouteById(routeId)));
        return routes;
    }

}
