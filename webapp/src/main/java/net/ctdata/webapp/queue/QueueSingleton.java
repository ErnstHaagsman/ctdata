package net.ctdata.webapp.queue;

import net.ctdata.common.Queue.RabbitMqConnection;

public class QueueSingleton {
    private static RabbitMqConnection conn;
    private static String rabbitMqUrl;

    public static void setUrl(String url){
        rabbitMqUrl = url;
    }

    public static RabbitMqConnection getConnection(){
        if(conn == null){
            try {
                conn = new RabbitMqConnection(rabbitMqUrl);
            } catch (Exception e) {

            }
        }

        return conn;
    }
}
