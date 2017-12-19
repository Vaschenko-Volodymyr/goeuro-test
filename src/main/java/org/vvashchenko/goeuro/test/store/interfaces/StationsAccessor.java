package org.vvashchenko.goeuro.test.store.interfaces;

import org.vvashchenko.goeuro.test.model.Station;
import org.springframework.stereotype.Component;
import org.vvashchenko.goeuro.test.utils.interfaces.Transactionable;

@Component
public interface StationsAccessor extends Accessor {

    Station getById(int id);

    void add(Station station);
}
