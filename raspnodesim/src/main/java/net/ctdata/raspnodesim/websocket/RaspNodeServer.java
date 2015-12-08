package net.ctdata.raspnodesim.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.ctdata.common.Messages.Abstract.AbstractMessage;
import net.ctdata.common.Messages.Confirmation;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Messages.UpdateFrequency;
import net.ctdata.raspnodeprotocol.WebsocketMessage;
import net.ctdata.raspnodesim.config.NodeConfiguration;
import net.ctdata.raspnodesim.observationcache.ObservationCache;
import net.ctdata.raspnodesim.router.DataListener;
import net.ctdata.raspnodesim.sensors.Sensor;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;

public class RaspNodeServer extends WebSocketServer implements DataListener {
    final ObservationCache cache;
    final NodeConfiguration configuration;

    public RaspNodeServer(ObservationCache cache, NodeConfiguration configuration){
        super(new InetSocketAddress(configuration.getWebsocketPort()));
        this.cache = cache;
        this.configuration = configuration;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        try {
            conn.send(new WebsocketMessage(configuration.getMetadata()).toJson());
            cache.GetObservations(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            AbstractMessage incoming = WebsocketMessage.fromJson(message).getPayload();
            if (incoming.getClass() == Confirmation.class){
                Confirmation confirmation = (Confirmation)incoming;
                cache.DeleteObservation(confirmation.getSensor(), confirmation.getTime());
            } else if (incoming.getClass() == UpdateFrequency.class){
                UpdateFrequency updateFrequency = (UpdateFrequency)incoming;
                for (Sensor s : configuration.getConnectedSensors()) {
                    if (s.getNumber() == updateFrequency.getSensor()) {
                        s.setPollingInterval(updateFrequency.getPollingFrequency());
                        System.out.println(String.format("Set node %d to update every %d ms",
                                updateFrequency.getSensor(),
                                updateFrequency.getPollingFrequency()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void NewObservation(Observation observation) {
        Collection<WebSocket> con = connections();
        String serializedObservation = null;
        try {
            serializedObservation = new WebsocketMessage(observation).toJson();
            synchronized ( con ) {
                for( WebSocket c : con ) {
                    c.send(serializedObservation);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
