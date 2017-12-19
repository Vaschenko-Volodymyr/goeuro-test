package org.vvashchenko.goeuro.test.routing;

import org.springframework.beans.factory.annotation.Qualifier;
import org.vvashchenko.goeuro.test.routing.interfaces.StationDAO;
import org.vvashchenko.goeuro.test.store.interfaces.StationsAccessor;
import org.vvashchenko.goeuro.test.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("inMemoryStationDAO")
public class InMemoryStationDAO implements StationDAO {

    @Autowired
    private StationsAccessor stationsAccessor;

    @Override
    public Station getStationById(int id) {
        return stationsAccessor.getById(id);
    }

}
