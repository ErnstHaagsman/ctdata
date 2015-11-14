package net.ctdata.common.Queue;

public interface QueueConnection {
    /**
     * Attach a listener to the message broker
     *
     * @param listener Listener to attach
     */
    void RegisterListener(QueueListener listener);

    /**
     * Send a message to the message broker. This method is thread-safe.
     *
     * @param message Message to send
     */
    void SendMessage(Message message);
}
