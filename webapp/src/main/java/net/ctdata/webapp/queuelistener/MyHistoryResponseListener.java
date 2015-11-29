package net.ctdata.webapp.queuelistener;

import net.ctdata.common.Messages.HistoryResponse;
import net.ctdata.common.Queue.Listeners.HistoryResponseListener;
import net.ctdata.common.Queue.RabbitMqConnection;

/**
 * Created by dhaval on 28-11-2015.
 */
public class MyHistoryResponseListener extends HistoryResponseListener{

    private RabbitMqConnection queueConn;

    public MyHistoryResponseListener(RabbitMqConnection queueConn){
        super();
        this.queueConn = queueConn;
    }

    @Override
    public void HandleMessage(HistoryResponse message) {

        HistoryResponse response = new HistoryResponse();
        response.setRequestId(message.getRequestId());
        response.setRaspberryNode(message.getRaspberryNode());
        response.setSensor(message.getSensor());
        response.setObservations(message.getObservations());

    }
}
