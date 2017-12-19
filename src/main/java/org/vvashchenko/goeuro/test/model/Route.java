package org.vvashchenko.goeuro.test.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class Route {

    @Getter
    @Setter
    private int routeId;

    @Getter
    @Setter
    private Set<Station> stations;

    public Route(int routeId) {
        this.routeId = routeId;
        this.stations = new HashSet<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && Route.class.equals(obj.getClass())) {
            Route that = (Route) obj;
            if (this.routeId == that.routeId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return routeId;
    }
}
