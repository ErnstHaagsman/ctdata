package net.ctdata.webapp.queue;

import net.ctdata.common.Queue.RabbitMqConnection;

public class QueueSingleton {
    private RabbitMqConnection conn;

    public RabbitMqConnection getConnection(){
        if(conn == null){
            try {
                conn = new RabbitMqConnection("amqp://admin:admin@ec2-52-35-1-0.us-west-2.compute.amazonaws.com:5672/myvhost");
            } catch (Exception e) {

            }
        }

        return conn;
    }
}
