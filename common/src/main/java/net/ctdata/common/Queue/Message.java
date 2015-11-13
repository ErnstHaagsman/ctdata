package net.ctdata.common.Queue;

public interface Message {
    String getRoutingKey();
    String getBody();
}
