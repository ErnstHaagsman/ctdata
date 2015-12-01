package net.ctdata.webapp.queue;

import net.ctdata.common.Queue.RabbitMqConnection;

public class QueueSingleton {
    private RabbitMqConnection conn;

    public RabbitMqConnection getConnection(){
        if(conn == null){
            try {
                conn = new RabbitMqConnection("amqp://localhost");
            } catch (Exception e) {

            }
        }

        return conn;
    }
}
