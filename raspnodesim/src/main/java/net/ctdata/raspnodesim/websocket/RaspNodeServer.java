package net.ctdata.raspnodesim.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.ctdata.common.Messages.Abstract.AbstractMessage;
import net.ctdata.common.Messages.Confirmation;
import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodeprotocol.WebsocketMessage;
import net.ctdata.raspnodesim.observationcache.ObservationCache;
import net.ctdata.raspnodesim.router.DataListener;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;

public class RaspNodeServer extends WebSocketServer implements DataListener {
    final ObservationCache cache;

    public RaspNodeServer(ObservationCache cache){
        super(new InetSocketAddress(8765));
        this.cache = cache;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        cache.GetObservations(this);
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
