package org.vvashchenko.goeuro.test.store;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.vvashchenko.goeuro.test.model.Station;
import org.vvashchenko.goeuro.test.store.interfaces.StationsAccessor;
import org.vvashchenko.goeuro.test.utils.BlockingTransactionManager;
import org.vvashchenko.goeuro.test.utils.interfaces.Transactionable;

@Component
@Qualifier("inMemoryStationsStore")
public class InMemoryStationsStore extends BlockingTransactionManager implements StationsAccessor {

    private Map<Integer, Station> stationStore = new HashMap<>();

    @Override
    public Station getById(int id) {
        checkTransactionStatus();
        return stationStore.get(id);
    }

    @Override
    public void add(Station station) {
        addNewStation(station);
    }

    private void addNewStation(Station station) {
        if(stationStore.containsKey(station.getStationId())) {
            Station oldStation = stationStore.get(station.getStationId());
            updateStationRoutes(oldStation, station);
        } else {
            stationStore.put(station.getStationId(), station);
        }
    }

    private void updateStationRoutes(Station oldStation, Station station){
        Collection<Integer> oldRoutesIds = oldStation.getRoutesIds();
        oldRoutesIds.addAll(station.getRoutesIds());
    }

    @Override
    public void clear() {
        stationStore.clear();
    }
}
