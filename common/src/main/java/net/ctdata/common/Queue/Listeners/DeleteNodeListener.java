package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.DeleteNode;

public abstract class DeleteNodeListener extends AbstractQueueListener<DeleteNode> {

    /**
     * Listener for AddNode messages
     */
    public DeleteNodeListener(){
        super(DeleteNode.class);
    }

    @Override
    public String getRoutingKeyPattern() {
        return "nodemgt.delete_node";
    }
}
