package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.Confirmation;

import java.util.UUID;

public abstract class ConfirmationListener extends AbstractQueueListener<Confirmation> {

    String routingKey = "datapoints.confirm.*.*";

    /**
     * Listens for all incoming confirmations on the network
     */
    public ConfirmationListener() {
        super(Confirmation.class);
    }

    /**
     * Listens for incoming confirmations for the specified Raspberry Node
     */
    public ConfirmationListener(UUID raspberryNodeId) {
        super(Confirmation.class);
        routingKey = String.format("datapoints.confirm.%s.*", raspberryNodeId);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }

}
