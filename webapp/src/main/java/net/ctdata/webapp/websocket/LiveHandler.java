package net.ctdata.webapp.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Messages.AddedNodesMetadata;
import net.ctdata.common.Messages.Partial.SensorLastObservation;
import net.ctdata.common.Messages.RaspberryLastObservation;
import net.ctdata.common.Queue.RabbitMqConnection;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class LiveHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessions = new LinkedList<WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        ObjectMapper mapper = new MapperSingleton().getMapper();
        AddedNodesMetadata anm = new AddedNodesMetadata();
        RaspberryLastObservation rlo = new RaspberryLastObservation();
        SensorLastObservation slo = new SensorLastObservation();
        slo.setName("Library Rainfall");
        slo.setSensor(1);
        slo.setLatitude(37.335571);
        slo.setLongitude(-121.884661);
        slo.setLastObservation(35.82);
        slo.setType("rainfall");
        rlo.getSensors().add(slo);
        anm.getRaspberryNodes().add(rlo);
        session.sendMessage(new TextMessage(mapper.writeValueAsString(anm)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }


}
