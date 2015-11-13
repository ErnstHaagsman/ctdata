package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.Confirmation;
import net.ctdata.common.Messages.Metadata;

import java.util.UUID;

public abstract class MetadataListener extends AbstractQueueListener<Metadata> {
    String routingKey = "nodedata.meta.*";

    /**
     * Listens for all metadata information messages on the network
     */
    public MetadataListener() {
        super(Metadata.class);
    }

    /**
     * Listens for metadata information for the specified node
     */
    public MetadataListener(UUID raspberryNodeId) {
        super(Metadata.class);
        routingKey = String.format("nodedata.meta.%s", raspberryNodeId);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }
}
