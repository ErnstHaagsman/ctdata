package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.Connect;

import java.util.UUID;

public abstract class ConnectListener extends AbstractQueueListener<Connect> {
    String routingKey = "nodemgt.connect.*";

    /**
     * Listener for all Connect messages
     */
    public ConnectListener(){
        super(Connect.class);
    }

    /**
     * Listener for all Connect messages for the specified gateway
     * @param gatewayId UUID for the gateway
     */
    public ConnectListener(UUID gatewayId){
        super(Connect.class);
        routingKey = String.format("nodemgt.connect.%s", gatewayId);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }
}
