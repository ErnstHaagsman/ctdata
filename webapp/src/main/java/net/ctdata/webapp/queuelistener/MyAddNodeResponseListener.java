package net.ctdata.webapp.queuelistener;

import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Queue.Listeners.MetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;

import java.util.UUID;

/**
 * Created by Nimesh on 11/28/2015.
 */
public class MyAddNodeResponseListener extends MetadataListener{
    private RabbitMqConnection queueConn;
    public MyAddNodeResponseListener(RabbitMqConnection queueConn){
        super();
        this.queueConn = queueConn;
    }
    public MyAddNodeResponseListener(UUID requestId, RabbitMqConnection queueConn){
        this.queueConn = queueConn;
    }
    String addedNodes;
    public String getAddedNodes() {
        return addedNodes;
    }
    public void HandleMessage(Metadata message) {
        // Tasks to be done
        // Task #1: update raspberry_node in Raspberry_Nodes table
        // Task #2: insert metadata into Sensors table
        // Task #3: Register observation listener for each sensor

        // Task #1: update raspberry_node in Raspberry_Nodes table
        System.out.println("Added Node");
        addedNodes = message.getNodeURL();
        System.out.println(String.format("Received metadata for node %s", message.getNodeURL()));

    }
}
