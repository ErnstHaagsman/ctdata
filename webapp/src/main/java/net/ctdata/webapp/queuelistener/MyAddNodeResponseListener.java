package net.ctdata.webapp.queuelistener;

import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Queue.Listeners.MetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;

/**
 * Created by Nimesh on 11/28/2015.
 */
public class MyAddNodeResponseListener extends MetadataListener{
    private RabbitMqConnection queueConn;
    private Metadata metadata;
//    AddedNode addedNode = new AddedNode();
//    public LinkedList<AddedNode> addedNodes = new LinkedList<AddedNode>();
    public MyAddNodeResponseListener(RabbitMqConnection queueConn){
        super();
        this.queueConn = queueConn;
    }
    public MyAddNodeResponseListener(){

    }

    public Metadata getMetadata() {
        return metadata;
    }

    //    public LinkedList<AddedNode> getAddedNodes() {
//        return addedNodes;
//    }
//    public void setAddedNodes() throws JsonProcessingException {
//        addedNodes = this.addedNodes;
//        /*
//        addedNode.setUrl("ws://localhost:8765");
//
//        addedNode.setNodeID(UUID.randomUUID());
//
//        addedNode.setSId(666);
//        addedNode.setSensorName("Temperature");
//        addedNode.setSensorType("32");
//        addedNode.setPollingInterval(50);
//        addedNode.setLatitude(36.485);
//        addedNode.setLongitude(-121.845);
//        addedNodes.add(addedNode);
//        */
//    }

        public void HandleMessage(Metadata message) {
            this.metadata = message;
            System.out.println(message.getNodeURL());
        }
//        // Tasks to be done
//        // Task #1: update raspberry_node in Raspberry_Nodes table
//        // Task #2: insert metadata into Sensors table
//        // Task #3: Register observation listener for each sensor
//
//        // Task #1: update raspberry_node in Raspberry_Nodes table
//
//        System.out.println("Added Node");
//        for (SensorMetadata s : message.getSensors()) {
//            addedNode.setNodeID(message.getRaspberryNode());
//            System.out.println(message.getRaspberryNode());
//            addedNode.setUrl(message.getNodeURL());
//            addedNode.setSId(s.getSensor());
//            addedNode.setSensorName(s.getName());
//            System.out.println(s.getName());
//            addedNode.setSensorType(s.getType());
//            addedNode.setPollingInterval(s.getPollingInterval());
//            addedNode.setLatitude(s.getLatitude());
//            addedNode.setLongitude(s.getLongitude());
//            addedNodes.add(addedNode);
//            System.out.println(addedNode.getNodeID());
//            addedNode = new AddedNode();
//        }
//            //System.out.println(addedNodes.toString());
//            addedNode.setAddedNodes(addedNodes);
//            System.out.println("Added Node"+addedNodes);
//            System.out.println(String.format("Received metadata for node %s", message.getNodeURL()));
//



}
