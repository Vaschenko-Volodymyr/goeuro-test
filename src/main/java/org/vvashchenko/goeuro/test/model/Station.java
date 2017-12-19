package org.vvashchenko.goeuro.test.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class Station {

    @Getter
    @Setter
    private int stationId;

    @Getter
    @Setter
    private Set<Integer> routesIds;

    public Station(int stationId) {
        this.stationId = stationId;
        this.routesIds = new HashSet<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && Station.class.equals(obj.getClass())) {
            Station that = (Station) obj;
            if (this.stationId == that.stationId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return stationId;
    }
}
