package net.ctdata.sensorgateway.websocket;

import net.ctdata.common.Messages.Abstract.AbstractMessage;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.raspnodeprotocol.WebsocketMessage;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;

public class RaspNodeClient extends WebSocketClient {
    RabbitMqConnection conn;

    public RaspNodeClient(URI nodeURL, RabbitMqConnection conn){
        super(nodeURL);
        this.conn = conn;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        try {
            AbstractMessage incoming = WebsocketMessage.fromJson(message).getPayload();
            if (incoming.getClass() == Observation.class){
                Observation observation = (Observation)incoming;
                System.out.println(String.format("Forwarded observation [Node: %s, Sensor: %d, Data: %f]", observation.getRaspberryNode(), observation.getSensor(), observation.getObservation()));
                conn.SendMessage(observation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
