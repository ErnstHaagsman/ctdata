package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.AbstractMessage;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by aditi on 21/11/15.
 */
public class AddedNodesMetadata extends AbstractMessage {

    private UUID requestId;
    private List<RaspberryLastObservation> raspberryNodes = new LinkedList<RaspberryLastObservation>();

    /**
     * Randomly generated unique request Id, this should match the request Id of the corresponding RequestAddedNodes message
     * @return {UUID}
     */
    public UUID getRequestId() {
        return requestId;
    }


    /**
     * @see {@link AddedNodesMetadata#requestId}
     */
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }


    /**
     * The metadata of all added raspberry nodes with their last observation
     * @return {List<RaspberryLastObservation>}
     */
    public List<RaspberryLastObservation> getRaspberryNodes() {
        return raspberryNodes;
    }


    /**
     * @see {@link AddedNodesMetadata#raspberryNodes}
     */
    public void setRaspberryNodes(List<RaspberryLastObservation> raspberryNodes) {
        this.raspberryNodes = raspberryNodes;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("nodemgt.added_nodes_metadata.%s", getRequestId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddedNodesMetadata that = (AddedNodesMetadata) o;
        return Objects.equals(requestId, that.requestId) &&
                Objects.equals(raspberryNodes, that.raspberryNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, raspberryNodes);
    }
}
