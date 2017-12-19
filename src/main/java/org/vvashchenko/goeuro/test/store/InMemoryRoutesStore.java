package org.vvashchenko.goeuro.test.store;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.vvashchenko.goeuro.test.model.Route;
import org.vvashchenko.goeuro.test.store.interfaces.RoutesAccessor;
import org.vvashchenko.goeuro.test.utils.BlockingTransactionManager;
import org.vvashchenko.goeuro.test.utils.interfaces.Transactionable;

@Component
@Qualifier("inMemoryRoutesStore")
public class InMemoryRoutesStore extends BlockingTransactionManager implements RoutesAccessor {

    private Map<Integer, Route> routesStore = new HashMap<>();

    @Override
    public Route getRouteById(int routeId) {
        checkTransactionStatus();
        return routesStore.get(routeId);
    }

    @Override
    public void addRoute(Route route) {
        routesStore.put(route.getRouteId(), route);
    }

    @Override
    public void clear() {
        routesStore.clear();
    }
}
