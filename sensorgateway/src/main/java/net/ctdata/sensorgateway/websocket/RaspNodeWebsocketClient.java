package net.ctdata.sensorgateway.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.ctdata.common.Messages.Abstract.AbstractMessage;
import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Messages.UpdateFrequency;
import net.ctdata.common.Queue.Listeners.UpdateFrequencyListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.raspnodeprotocol.WebsocketMessage;
import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

class RaspNodeWebsocketClient extends WebSocketClient {
    static Logger logger = Logger.getLogger(RaspNodeWebsocketClient.class);
    final RabbitMqConnection conn;
    final RaspNodeClient parent;
    UpdateFrequencyListener frequencyListener;

    class SensorGatewayUpdateFrequencyListener extends UpdateFrequencyListener {
        SensorGatewayUpdateFrequencyListener(UUID raspNodeID){
            super(raspNodeID);
        }

        @Override
        public void HandleMessage(UpdateFrequency message) throws JsonProcessingException {
            send(new WebsocketMessage(message).toJson());
        }
    }

    public RaspNodeWebsocketClient(URI nodeURL, RabbitMqConnection conn, RaspNodeClient parent){
        super(nodeURL);
        this.conn = conn;
        this.parent = parent;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onMessage(String message) {
        try {
            AbstractMessage incoming = WebsocketMessage.fromJson(message).getPayload();
            Class incomingClass = incoming.getClass();
            if (incomingClass == Metadata.class){
                Metadata metadata = (Metadata)incoming;
                logger.info(String.format("Received metadata for node %s", metadata.getRaspberryNode()));
                frequencyListener = new SensorGatewayUpdateFrequencyListener(metadata.getRaspberryNode());
                conn.RegisterListener(frequencyListener);
                parent.setUUID(metadata.getRaspberryNode());
                metadata.setNodeURL(this.getURI().toString());
                conn.SendMessage(metadata);
            } else if (incomingClass == Observation.class){
                Observation observation = (Observation)incoming;
                logger.debug(String.format("Forwarded observation [Node: %s, Sensor: %d, Data: %f]", observation.getRaspberryNode(), observation.getSensor(), observation.getObservation()));
                conn.SendMessage(observation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info(String.format("Lost connection to %s. Code %d, reason: %s", this.getURI().toString(), code, reason));
        parent.onClose();
    }

    @Override
    public void onError(Exception ex) {
        logger.warn(String.format("Connection error for connection %s", this.getURI().toString()));
        logger.warn(ex);
    }
}
