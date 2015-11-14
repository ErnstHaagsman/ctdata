package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.Observation;

import java.util.UUID;

public abstract class ObservationListener extends AbstractQueueListener<Observation> {
    String routingKey = "datapoints.incoming.*.*";

    /**
     * Listens for all incoming observations on the network
     */
    public ObservationListener() {
        super(Observation.class);
    }

    /**
     * Listens for incoming observations from the specified sensor
     */
    public ObservationListener(UUID raspberryNodeId, int sensor) {
        super(Observation.class);
        routingKey = String.format("datapoints.incoming.%s.%d", raspberryNodeId, sensor);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }
}
