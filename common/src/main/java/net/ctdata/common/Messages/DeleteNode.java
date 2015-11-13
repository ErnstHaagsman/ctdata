package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.RaspberryMessage;

public class DeleteNode extends RaspberryMessage {
    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return "nodemgt.delete_node";
    }
}
