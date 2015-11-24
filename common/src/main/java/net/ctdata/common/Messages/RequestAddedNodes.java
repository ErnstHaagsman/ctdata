package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.AbstractMessage;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by aditi on 21/11/15.
 */
public class RequestAddedNodes extends AbstractMessage{

    private UUID requestId;
    private String userId;
    private String interfaceType;

    /**
     * Randomly generated unique request Id
     * @return {UUID}
     */
    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    /**
     * User's unique login user Id
     * @return {String}
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Web UI interface type. It can be "Administrator" or "Public"
     * @return {String}
     */
    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return "nodemgt.request_added_nodes";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestAddedNodes that = (RequestAddedNodes) o;
        return Objects.equals(requestId, that.requestId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(interfaceType, that.interfaceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, userId, interfaceType);
    }
}
