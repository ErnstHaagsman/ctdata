package net.ctdata.common.Queue.Listeners;

import net.ctdata.common.Messages.Metadata;

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
    public MetadataListener(String nodeUrl) {
        super(Metadata.class);
        routingKey = String.format("nodedata.meta.%s", nodeUrl);
    }

    @Override
    public String getRoutingKeyPattern() {
        return routingKey;
    }
}
