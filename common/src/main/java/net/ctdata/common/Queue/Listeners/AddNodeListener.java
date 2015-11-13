package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.AddNode;

public abstract class AddNodeListener extends AbstractQueueListener<AddNode> {

    /**
     * Listener for AddNode messages
     */
    public AddNodeListener(){
        super(AddNode.class);
    }

    @Override
    public String getRoutingKeyPattern() {
        return "nodemgt.add_node";
    }
}
