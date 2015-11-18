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
        final RabbitMqConnection conn = new RabbitMqConnection("amqp://localhost");

        conn.RegisterListener(new AddNodeListener() {
            @Override
            public void HandleMessage(AddNode message) {
                Connect connectMessage = new Connect();
                connectMessage.setGatewayId(UUID.randomUUID());
                connectMessage.setNodeURL(message.getNodeURL());
                conn.SendMessage(connectMessage);
            }
        });
    }
}
