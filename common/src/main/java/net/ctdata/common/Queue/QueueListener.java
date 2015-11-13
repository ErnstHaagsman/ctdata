package net.ctdata.common.Queue;

public interface QueueListener {
    /**
     * The RabbitMQ binding that this listener listens to
     *
     * @see <a href="https://www.rabbitmq.com/tutorials/tutorial-five-java.html">RabbitMQ Documentation</a>
     * @return {String}
     */
    String getRoutingKeyPattern();

    /**
     * {@link RabbitMqConnection} will call this method with any message received for the binding in
     * {@link QueueListener#getRoutingKeyPattern()}
     *
     * @param message The received message
     */
    void HandleMessage(Message message);
}
