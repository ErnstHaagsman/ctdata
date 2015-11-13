package net.ctdata.common.Messages;

import net.ctdata.common.Messages.Abstract.RaspberryMessage;

public class DeleteNode extends RaspberryMessage {
    @Override
    public String getRoutingKey() {
        return "nodemgt.delete_node";
    }
}
