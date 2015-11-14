package net.ctdata.sensorgateway;

import net.ctdata.common.Messages.Connect;
import net.ctdata.common.Queue.Listeners.ConnectListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.sensorgateway.websocket.RaspNodeClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class SensorGateway {
    public static void main(String[] args) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
        final RabbitMqConnection conn = new RabbitMqConnection("amqp://localhost");
        final List<RaspNodeClient> connectedNodes = new LinkedList<RaspNodeClient>();

        conn.RegisterListener(new ConnectListener() {
            @Override
            public void HandleMessage(Connect message) {
                RaspNodeClient client = new RaspNodeClient(URI.create(message.getNodeURL()), conn);
                client.connect();
                connectedNodes.add(client);
            }
        });
    }
}
