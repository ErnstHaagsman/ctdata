package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.Disconnect;

import java.util.UUID;

public abstract class DisconnectListener extends AbstractQueueListener<Disconnect> {
    String routingKey = "nodemgt.disconnect.*";

    /**
     * Listener for all Disconnect messages
     */
    public DisconnectListener(){
        super(Disconnect.class);
    }

    /**
     * Listener for Disconnect messages for the specified Raspberry node
     * @param raspberryNodeId Listen for disconnect messages for this node
     */
    public DisconnectListener(UUID raspberryNodeId){
        super(Disconnect.class);
        routingKey = String.format("nodemgt.disconnect.%s", raspberryNodeId);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }
}
