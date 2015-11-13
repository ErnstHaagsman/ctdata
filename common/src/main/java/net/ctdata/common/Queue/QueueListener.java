package net.ctdata.common.Queue;

import net.ctdata.common.Messages.AbstractMessage;

public interface QueueListener {
    String getRoutingKeyPattern();
    void HandleMessage(Message message);
}
