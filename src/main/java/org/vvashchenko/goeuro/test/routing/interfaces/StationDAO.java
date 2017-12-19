package org.vvashchenko.goeuro.test.routing.interfaces;

import org.vvashchenko.goeuro.test.model.Station;
import org.springframework.stereotype.Repository;

@Repository
public interface StationDAO {

    Station getStationById(int id);

}
