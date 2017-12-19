package org.vvashchenko.goeuro.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vvashchenko.goeuro.test.rest.RouteController;
import org.vvashchenko.goeuro.test.routing.InMemoryRoutesDAO;
import org.vvashchenko.goeuro.test.routing.InMemoryStationDAO;
import org.vvashchenko.goeuro.test.routing.interfaces.RoutesDAO;
import org.vvashchenko.goeuro.test.routing.interfaces.StationDAO;
import org.vvashchenko.goeuro.test.service.RouteServiceImpl;
import org.vvashchenko.goeuro.test.service.interfaces.RouteService;
import org.vvashchenko.goeuro.test.store.InMemoryRoutesStore;
import org.vvashchenko.goeuro.test.store.InMemoryStationsStore;
import org.vvashchenko.goeuro.test.store.interfaces.RoutesAccessor;
import org.vvashchenko.goeuro.test.store.interfaces.StationsAccessor;
import org.vvashchenko.goeuro.test.utils.BlockingTransactionManager;
import org.vvashchenko.goeuro.test.utils.InMemoryStoreInitializer;
import org.vvashchenko.goeuro.test.utils.interfaces.StoreInitializer;
import org.vvashchenko.goeuro.test.utils.interfaces.Transactionable;

@Configuration
public class TestConfig {

    @Bean
    @Qualifier("InMemoryStoreInitializer")
    public StoreInitializer getStoreInitializer() {
        return new InMemoryStoreInitializer();
    }

    @Bean
    @Qualifier("inMemoryRoutesStore")
    public RoutesAccessor getRouteAccessor() {
        return new InMemoryRoutesStore();
    }

    @Bean
    @Qualifier("inMemoryStationsStore")
    public StationsAccessor getStationAccessor() {
        return new InMemoryStationsStore();
    }

    @Bean
    @Qualifier("BlockingTransactionManager")
    public Transactionable getTransactionable() {
        return new BlockingTransactionManager();
    }

    @Bean
    @Qualifier("RouteServiceImpl")
    public RouteService getRouteService() {
        return new RouteServiceImpl();
    }

    @Bean
    @Qualifier("inMemoryRoutesDAO")
    public RoutesDAO getRoutesDAO() {
        return new InMemoryRoutesDAO();
    }

    @Bean
    @Qualifier("inMemoryStationDAO")
    public StationDAO getStationDAO() {
        return new InMemoryStationDAO();
    }

    @Bean
    public RouteController getRouteController() {
        return new RouteController();
    }
}
