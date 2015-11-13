package net.ctdata.common.Queue;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.rabbitmq.client.*;

public class MessageSender implements Runnable {
    BlockingQueue<Message> queue;
    Channel channel;
    String exchangeName;

    public MessageSender(Connection connection, String exchangeName) throws IOException {
        this.queue = new LinkedBlockingQueue<Message>();
        this.channel = connection.createChannel();
        this.exchangeName = exchangeName;

        this.channel.exchangeDeclare(exchangeName, "topic");
    }

    public void queue(Message message) throws InterruptedException{
        queue.put(message);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Message toSend = queue.take();
                this.channel.basicPublish(exchangeName, toSend.getRoutingKey(), null, toSend.getBody().getBytes());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
