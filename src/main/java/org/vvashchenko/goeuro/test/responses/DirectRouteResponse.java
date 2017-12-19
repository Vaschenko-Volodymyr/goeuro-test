package org.vvashchenko.goeuro.test.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class DirectRouteResponse implements Serializable {
    @Getter
    @Setter
    @JsonProperty("dep_sid")
    private int depSid;

    @Getter
    @Setter
    @JsonProperty("arr_sid")
    private int arrSid;

    @Getter
    @Setter
    @JsonProperty("direct_bus_route")
    private boolean directBusRoute;

    public DirectRouteResponse(int depSid, int arrSid, boolean directBusRoute) {
        this.depSid = depSid;
        this.arrSid = arrSid;
        this.directBusRoute = directBusRoute;
    }
}
