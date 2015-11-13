package net.ctdata.common.Queue;

public interface Message {
    /**
     * The routing key used with this message
     *
     * @see {@link QueueListener#getRoutingKeyPattern()}
     * @return {String}
     */
    String getRoutingKey();

    /**
     * Body text of the message
     *
     * @return {String}
     */
    String getBody();
}
