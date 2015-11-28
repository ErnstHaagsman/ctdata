package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.RequestAddedNodes;

/**
 * Created by aditi on 21/11/15.
 */
public abstract class RequestAddedNodesListener extends AbstractQueueListener<RequestAddedNodes> {

    /**
     * Listener for RequestAddedNodes messages
     */
    public RequestAddedNodesListener(){
        super(RequestAddedNodes.class);
    }

    @Override
    public String getRoutingKeyPattern() {
        return "nodemgt.request_added_nodes";
    }
}
