package net.ctdata.webapp.queuelistener;

import net.ctdata.common.Messages.AddedNodesMetadata;
import net.ctdata.common.Messages.Partial.SensorLastObservation;
import net.ctdata.common.Messages.RaspberryLastObservation;
import net.ctdata.common.Queue.Listeners.AddedNodesMetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.controllers.AddedNode;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Nimesh on 11/27/2015.
 */

public class MyAddedNodeRequestListener extends AddedNodesMetadataListener {

    private RabbitMqConnection queueConn;
    AddedNode addedNode = new AddedNode();
    String url;
    UUID nodeID;
    double sId;
    String sensorName;
    String sensorType;
    int pollingInterval;
    double latitude;
    double longitude;

    LinkedList<AddedNode> addedNodes = new LinkedList<AddedNode>();
    public MyAddedNodeRequestListener(RabbitMqConnection queueConn){
        super();
        this.queueConn = queueConn;
    }

    public MyAddedNodeRequestListener(UUID requestId, RabbitMqConnection queueConn){
        super(requestId);
        this.queueConn = queueConn;
    }

    public LinkedList<AddedNode> getAddedNodes() {
        return addedNodes;
    }

    public void setAddNode(){
        AddedNode addedNode = new AddedNode();
            addedNode.setUrl("ws://localhost:8765");

            addedNode.setNodeID(UUID.randomUUID());

                addedNode.setSId(666);
                addedNode.setSensorName("Temperature");
                addedNode.setSensorType("32");
                addedNode.setPollingInterval(50);
                addedNode.setLastObservation(23.424);
                addedNode.setLatitude(36.485);
                addedNode.setLongitude(-121.845);
                addedNodes.add(addedNode);
         addedNode = new AddedNode();
        addedNode.setUrl("ws://localhost:8735");

        addedNode.setNodeID(UUID.randomUUID());

        addedNode.setSId(666);
        addedNode.setSensorName("Temperature");
        addedNode.setSensorType("72");
        addedNode.setPollingInterval(20);
        addedNode.setLastObservation(23.424);
        addedNode.setLatitude(36.485);
        addedNode.setLongitude(-121.845);
        addedNodes.add(addedNode);
        addedNode = new AddedNode();
        addedNode.setUrl("ws://localhost:8715");

        addedNode.setNodeID(UUID.randomUUID());

        addedNode.setSId(266);
        addedNode.setSensorName("Temperature");
        addedNode.setSensorType("32");
        addedNode.setPollingInterval(58);
        addedNode.setLastObservation(23.424);
        addedNode.setLatitude(36.485);
        addedNode.setLongitude(-121.845);
        addedNodes.add(addedNode);
    }

    @Override
    public void HandleMessage(AddedNodesMetadata message) {
        // Tasks to be done
        // Task #1: update raspberry_node in Raspberry_Nodes table
        // Task #2: insert metadata into Sensors table
        // Task #3: Register observation listener for each sensor

        // Task #1: update raspberry_node in Raspberry_Nodes table
        System.out.println("Added Node");
        System.out.println(String.format("Received metadata for node %s", message.getRaspberryNodes()));

        for (RaspberryLastObservation r: message.getRaspberryNodes()) {

            addedNode.setUrl(r.getNodeURL());

            addedNode.setNodeID(r.getRaspberryNode());
            for (SensorLastObservation s : r.getSensors()) {
                addedNode.setSId(s.getSensor());
                addedNode.setSensorName(s.getName());
                addedNode.setSensorType(s.getType());
                addedNode.setLastObservation(s.getLastObservation());
                addedNode.setPollingInterval(s.getPollingInterval());
                addedNode.setLatitude(s.getLatitude());
                addedNode.setLongitude(s.getLongitude());
                addedNodes.add(addedNode);
                addedNode = new AddedNode();
            }
        }
    }
}
