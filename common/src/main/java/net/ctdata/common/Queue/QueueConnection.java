package net.ctdata.common.Queue;

public interface QueueConnection {
    void RegisterListener(QueueListener listener);
    void SendMessage(Message message);
}
