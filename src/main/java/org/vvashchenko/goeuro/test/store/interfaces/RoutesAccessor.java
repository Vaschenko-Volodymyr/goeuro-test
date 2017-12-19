package org.vvashchenko.goeuro.test.store.interfaces;

import org.vvashchenko.goeuro.test.model.Route;
import org.springframework.stereotype.Component;
import org.vvashchenko.goeuro.test.utils.interfaces.Transactionable;

@Component
public interface RoutesAccessor extends Accessor {

    Route getRouteById(int routeId);

    void addRoute(Route route);

}
