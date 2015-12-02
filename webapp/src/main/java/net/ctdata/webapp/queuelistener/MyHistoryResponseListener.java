package net.ctdata.webapp.queuelistener;

import net.ctdata.common.Messages.HistoryResponse;
import net.ctdata.common.Queue.Listeners.HistoryResponseListener;
import net.ctdata.common.Queue.RabbitMqConnection;

import java.util.UUID;

/**
 * Created by dhaval on 28-11-2015.
 */
public class MyHistoryResponseListener extends HistoryResponseListener{

    private RabbitMqConnection queueConn;
    HistoryResponse response = new HistoryResponse();

    public MyHistoryResponseListener(RabbitMqConnection queueConn){
        super(UUID.randomUUID());
        this.queueConn = queueConn;
    }

    public HistoryResponse getHistoryResponse() {
        return response;
    }

    @Override
    public void HandleMessage(HistoryResponse message) {

        response.setRequestId(message.getRequestId());
        response.setRaspberryNode(message.getRaspberryNode());
        response.setSensor(message.getSensor());
        response.setObservations(message.getObservations());

    }
}
