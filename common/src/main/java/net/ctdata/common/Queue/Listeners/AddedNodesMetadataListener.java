package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.AddedNodesMetadata;

import java.util.UUID;

/**
 * Created by aditi on 21/11/15.
 */
public abstract class AddedNodesMetadataListener extends AbstractQueueListener<AddedNodesMetadata> {
    String routingKey = "nodemgt.added_nodes_metadata.*";

    /**
     * Listener for all AddedNodesMetadata messages
     */
    public AddedNodesMetadataListener(){
        super(AddedNodesMetadata.class);
    }

    /**
     * Listener for AddedNodesMetadata messages corresponding to a particular request Id
     * @param {UUID}
     */
    public AddedNodesMetadataListener(UUID requestId){
        super(AddedNodesMetadata.class);
        routingKey = String.format("nodemgt.added_nodes_metadata.%s", requestId);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }
}
