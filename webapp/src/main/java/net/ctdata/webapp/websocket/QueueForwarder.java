package net.ctdata.webapp.websocket;

import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.Listeners.MetadataListener;
import net.ctdata.common.Queue.Listeners.ObservationListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.queue.QueueSingleton;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class QueueForwarder {
    private RabbitMqConnection conn;
    private List<WebSocketSession> sessions = new LinkedList<WebSocketSession>();

    public QueueForwarder(){
        conn = QueueSingleton.getConnection();
        conn.RegisterListener(new ObservationListener() {
            @Override
            public void HandleMessage(Observation message) {
                for (WebSocketSession session : sessions){
                    try {
                        session.sendMessage(new TextMessage(message.getBody()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        conn.RegisterListener(new MetadataListener() {
            @Override
            public void HandleMessage(Metadata message) {
                for (WebSocketSession session : sessions){
                    try {
                        session.sendMessage(new TextMessage(message.getBody()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    void addSession(WebSocketSession session){
        sessions.add(session);
    }

    void removeSession(WebSocketSession session){
        sessions.remove(session);
    }
}
