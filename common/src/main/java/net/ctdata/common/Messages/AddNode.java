package net.ctdata.common.Messages;

import net.ctdata.common.Messages.Abstract.AbstractMessage;

public class AddNode extends AbstractMessage {
    private String nodeURL;

    /**
     * URL of the node to add
     * @return {String}
     */
    public String getNodeURL() {
        return nodeURL;
    }

    /**
     * @see {@link AddNode#getNodeURL()}
     */
    public void setNodeURL(String nodeURL) {
        this.nodeURL = nodeURL;
    }

    @Override
    public String getRoutingKey() {
        return "nodemgt.add_node";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddNode addNode = (AddNode) o;

        return !(nodeURL != null ? !nodeURL.equals(addNode.nodeURL) : addNode.nodeURL != null);

    }

    @Override
    public int hashCode() {
        return nodeURL != null ? nodeURL.hashCode() : 0;
    }
}
