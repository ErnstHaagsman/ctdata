package net.ctdata.raspnodesim.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodeprotocol.WebsocketMessage;
import net.ctdata.raspnodesim.router.DataListener;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collection;

public class RaspNodeServer extends WebSocketServer implements DataListener {
    public RaspNodeServer(){
        super(new InetSocketAddress(8765));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

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
