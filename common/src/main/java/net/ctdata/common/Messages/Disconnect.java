package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.RaspberryMessage;

public class Disconnect extends RaspberryMessage {
    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("nodemgt.disconnect.%s", getRaspberryNode());
    }
}
