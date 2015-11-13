package net.ctdata.common.Queue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RabbitMqConnection implements QueueConnection {
    Connection connection;
    private final String EXCHANGE_NAME = "CTDATA";
    private MessageSender sender;

    @Override
    public void RegisterListener(final QueueListener listener) {
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, listener.getRoutingKeyPattern());

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String bodyString = new String(body, "UTF-8");
                    listener.HandleMessage(new DefaultMessage(envelope.getRoutingKey(), bodyString));
                }
            };
            channel.basicConsume(queueName,consumer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void SendMessage(Message message) {
        try {
            sender.queue(message);
        } catch (InterruptedException e) {

            System.out.println(e.getMessage());

        }
    }

    public RabbitMqConnection(String uri) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        connection = factory.newConnection();

        sender = new MessageSender(connection, EXCHANGE_NAME);
        Thread sendThread = new Thread(sender);
        sendThread.start();
    }
}
