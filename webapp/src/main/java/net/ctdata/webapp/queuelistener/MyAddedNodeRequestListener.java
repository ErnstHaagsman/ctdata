package net.ctdata.webapp.queuelistener;

import net.ctdata.common.Messages.AddedNodesMetadata;
import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Messages.Partial.SensorMetadata;
import net.ctdata.common.Queue.Listeners.AddedNodesMetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.controllers.AddedNode;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Nimesh on 11/27/2015.
 */

public class MyAddedNodeRequestListener extends AddedNodesMetadataListener {

    private RabbitMqConnection queueConn;
    ArrayList<AddedNode> addedNodes = new ArrayList<AddedNode>();
    public MyAddedNodeRequestListener(RabbitMqConnection queueConn){
        super();
        this.queueConn = queueConn;
    }

    public MyAddedNodeRequestListener(UUID requestId, RabbitMqConnection queueConn){
        super(requestId);
        this.queueConn = queueConn;
    }

    public ArrayList<AddedNode> getAddedNodes() {
        return addedNodes;
    }

    @Override
    public void HandleMessage(AddedNodesMetadata message) {
        // Tasks to be done
        // Task #1: update raspberry_node in Raspberry_Nodes table
        // Task #2: insert metadata into Sensors table
        // Task #3: Register observation listener for each sensor

        // Task #1: update raspberry_node in Raspberry_Nodes table


        AddedNode addedNode = new AddedNode();
        String url;
        UUID nodeID;
        double sId;
        String sensorName;
        String sensorType;
        int pollingInterval;
        double latitude;
        double longitude;
        for (Metadata r: message.getRaspberryNodes()) {
            addedNode.setUrl(r.getNodeURL());
            addedNode.setNodeID(r.getRaspberryNode());
            for (SensorMetadata s : r.getSensors()) {
                addedNode.setSId(s.getSensor());
                addedNode.setSensorName(s.getName());
                addedNode.setSensorType(s.getType());
                addedNode.setPollingInterval(s.getPollingInterval());
                addedNode.setLatitude(s.getLatitude());
                addedNode.setLongitude(s.getLongitude());
            addedNodes.add(addedNode);
            }
        }
    }
}
