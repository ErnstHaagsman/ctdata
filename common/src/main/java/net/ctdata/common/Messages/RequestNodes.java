package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.AbstractMessage;

import java.util.UUID;

public class RequestNodes extends AbstractMessage {
    private UUID gatewayId;

    /**
     * The gateway for which to request the connect messages
     * @return {UUID}
     */
    public UUID getGatewayId() {
        return gatewayId;
    }

    /**
     * @see {@link RequestNodes#getGatewayId()}
     */
    public void setGatewayId(UUID gatewayId) {
        this.gatewayId = gatewayId;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return "nodemgt.request_nodes_for_gateway";
    }
}
