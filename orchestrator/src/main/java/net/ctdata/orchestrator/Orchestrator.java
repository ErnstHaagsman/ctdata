package net.ctdata.orchestrator;

import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Messages.Connect;
import net.ctdata.common.Queue.Listeners.AddNodeListener;
import net.ctdata.common.Queue.RabbitMqConnection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;


public class Orchestrator {


    public static void main(String[] args) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {


        System.out.println("Orchestratation server started successfully!!");
        System.out.println("Setting up the RabbitMQ connection..");
        final RabbitMqConnection conn = new RabbitMqConnection("amqp://localhost");
        System.out.println("RabbitMQ connection established..");

        conn.RegisterListener(new AddNodeListener() {
            @Override
            public void HandleMessage(AddNode message) {
                System.out.println("ADD_NODE: Received message from user "+ message.getUserId()
                    + " for adding the raspberry node having URL " + message.getNodeURL());
                Connect connectMessage = new Connect();
                connectMessage.setGatewayId(UUID.randomUUID());
                connectMessage.setNodeURL(message.getNodeURL());
                conn.SendMessage(connectMessage);
                System.out.println("CONNECT: Sent message for node URL "+ message.getNodeURL()
                    + " and gateway Id "+ connectMessage.getGatewayId());
            }
        });
    }
}
