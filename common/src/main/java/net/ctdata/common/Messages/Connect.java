package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Connect extends RequestNodes {
    private String nodeURL;

    /**
     * URL of Raspberry Node to connect to
     * @return {string}
     */
    public String getNodeURL() {
        return nodeURL;
    }

    /**
     * @see {@link Connect#getNodeURL()}
     */
    public void setNodeURL(String nodeURL) {
        this.nodeURL = nodeURL;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("nodemgt.connect.%s", getGatewayId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connect connect = (Connect) o;

        return !(nodeURL != null ? !nodeURL.equals(connect.nodeURL) : connect.nodeURL != null);

    }

    @Override
    public int hashCode() {
        return nodeURL != null ? nodeURL.hashCode() : 0;
    }
}
