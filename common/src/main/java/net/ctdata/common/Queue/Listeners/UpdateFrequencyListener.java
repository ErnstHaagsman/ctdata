package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.UpdateFrequency;

import java.util.UUID;

public abstract class UpdateFrequencyListener extends AbstractQueueListener<UpdateFrequency> {
    String routingKey = "nodedata.updatefrequency.*";

    /**
     * Listener for all UpdateFrequency messages
     */
    public UpdateFrequencyListener(){
        super(UpdateFrequency.class);
    }

    /**
     * Listener for UpdateFrequency messages for the specified raspberry node
     * @param raspberryNodeId The raspberry node for which to listen for UpdateFrequency messages
     */
    public UpdateFrequencyListener(UUID raspberryNodeId){
        super(UpdateFrequency.class);
        routingKey = String.format("nodedata.updatefrequency.%s", raspberryNodeId);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }
}
