package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.RequestNodes;

public abstract class RequestNodesListener extends AbstractQueueListener<RequestNodes> {

    /**
     * Listener for RequestNodes messages
     */
    public RequestNodesListener(){
        super(RequestNodes.class);
    }

    @Override
    public String getRoutingKeyPattern() {
        return "nodemgt.request_nodes_for_gateway";
    }
}
