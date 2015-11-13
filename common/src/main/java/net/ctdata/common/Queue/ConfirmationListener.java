package net.ctdata.common.Queue;

import net.ctdata.common.Messages.Confirmation;

import java.util.UUID;

public abstract class ConfirmationListener extends AbstractQueueListener<Confirmation> {

    String routingKey = "datapoints.confirm.*.*";

    /**
     * Listens for all incoming observations on the network
     */
    public ConfirmationListener() {
        super(Confirmation.class);
    }

    /**
     * Listens for incoming observations from the specified sensor
     */
    public ConfirmationListener(UUID raspberryNodeId, int sensor) {
        super(Confirmation.class);
        routingKey = String.format("datapoints.incoming.%s.%d", raspberryNodeId, sensor);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }

}
