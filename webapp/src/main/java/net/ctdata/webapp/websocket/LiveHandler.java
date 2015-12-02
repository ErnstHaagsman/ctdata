package net.ctdata.webapp.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Messages.AddedNodesMetadata;
import net.ctdata.common.Messages.HistoryRequest;
import net.ctdata.common.Messages.HistoryResponse;
import net.ctdata.common.Messages.RequestAddedNodes;
import net.ctdata.common.Queue.Listeners.AddedNodesMetadataListener;
import net.ctdata.common.Queue.Listeners.HistoryResponseListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.messages.HistoryRequestMessage;
import net.ctdata.webapp.messages.HistoryResponseMessage;
import net.ctdata.webapp.queue.QueueSingleton;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.UUID;

public class LiveHandler extends TextWebSocketHandler {

    private static QueueForwarder forwarder = new QueueForwarder();


    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        forwarder.addSession(session);
        final ObjectMapper mapper = new MapperSingleton().getMapper();

        RequestAddedNodes ran = new RequestAddedNodes();
        ran.setRequestId(UUID.randomUUID());

        RabbitMqConnection conn = new QueueSingleton().getConnection();
        conn.RegisterListener(new AddedNodesMetadataListener(ran.getRequestId()) {
            @Override
            public void HandleMessage(AddedNodesMetadata message) {
                try {
                    session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        conn.SendMessage(ran);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        forwarder.removeSession(session);
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, TextMessage message) throws Exception {
        final ObjectMapper mapper = new MapperSingleton().getMapper();
        HistoryRequestMessage request = mapper.readValue(message.getPayload(), HistoryRequestMessage.class);

        HistoryRequest hr = new HistoryRequest();
        hr.setRaspberryNode(request.getRaspberryNode());
        hr.setSensor(request.getSensorNumber());
        hr.setTimePeriod(new Interval(DateTime.now().minusMinutes(10), DateTime.now()));
        System.out.println("Send request " + hr.getBody());

        RabbitMqConnection conn = new QueueSingleton().getConnection();
        conn.RegisterListener(new HistoryResponseListener(hr.getRequestID()) {
            @Override
            public void HandleMessage(HistoryResponse message) {
                try {
                    HistoryResponseMessage hrm = new HistoryResponseMessage(message);
                    session.sendMessage(new TextMessage(mapper.writeValueAsString(hrm)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        conn.SendMessage(hr);
    }


}
