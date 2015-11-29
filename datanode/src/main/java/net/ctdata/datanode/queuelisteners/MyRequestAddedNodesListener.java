package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.AddedNodesMetadata;
import net.ctdata.common.Messages.Partial.SensorLastObservation;
import net.ctdata.common.Messages.RaspberryLastObservation;
import net.ctdata.common.Messages.RequestAddedNodes;
import net.ctdata.common.Queue.Listeners.RequestAddedNodesListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.dbconnectors.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditi on 21/11/15.
 */
public class MyRequestAddedNodesListener extends RequestAddedNodesListener {
    static Logger logger = Logger.getLogger(MyRequestAddedNodesListener.class);
    private RabbitMqConnection conn;
    private DatabaseConnector dbConnector;

    public MyRequestAddedNodesListener(RabbitMqConnection conn, DatabaseConnector dbConnector){
        super();
        this.conn = conn;
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(RequestAddedNodes message) {

        logger.debug("REQUEST_ADDED_NODES: Received " + message.getRequestId() +" request from user "+ message.getUserId()
                + " for interface type "+ message.getInterfaceType());

        AddedNodesMetadata response = new AddedNodesMetadata();

        // Task #1

        UserSensorsConnector userSensorConn = new UserSensorsConnector(this.dbConnector);
        List<UserSensors> list = new ArrayList<UserSensors>();
        try{
            if(message.getInterfaceType().equalsIgnoreCase("Administrator"))
                list = userSensorConn.selectFrom(message.getUserId());
            else if(message.getInterfaceType().equalsIgnoreCase("Public"))
                list = userSensorConn.selectAll();
            if(list!=null) {
                if (list.size() >= 1) {
                    response = getResponse(list);
                    if(response == null) {
                        response = new AddedNodesMetadata();
                        response.setRaspberryNodes(null);
                    }
                    response.setRequestId(message.getRequestId());
                    logger.debug("REQUEST_ADDED_NODES: Sending the response message for request Id " + response.getRequestId());
                    conn.SendMessage(response);
                } else {
                    logger.debug("REQUEST_ADDED_NODES: No connected sensors for the interface type " + message.getInterfaceType() + " .. sending empty response");
                    response.setRequestId(message.getRequestId());
                    response.setRaspberryNodes(null);
                    conn.SendMessage(response);
                }
            }
            else {
                logger.debug("REQUEST_ADDED_NODES: No connected sensors for the interface type " + message.getInterfaceType() + " .. sending empty response");
                response.setRequestId(message.getRequestId());
                response.setRaspberryNodes(null);
                conn.SendMessage(response);
            }
        }catch (SQLException ex){
            logger.error("SQLException: Exception thrown while fetching data from the database due to "+ ex.getMessage());
        }
    }

    public AddedNodesMetadata getResponse(List<UserSensors> list){

        if(list!=null){
            try{
                AddedNodesMetadata response = new AddedNodesMetadata();
                RaspberryNodesConnector raspConn = new RaspberryNodesConnector(this.dbConnector);
                SensorsConnector senConn = new SensorsConnector(this.dbConnector);
                ObservationsConnector obsConn = new ObservationsConnector(this.dbConnector);

                List<RaspberryLastObservation> addedNodes = new ArrayList<RaspberryLastObservation>();

                for(UserSensors eachNode: list) {
                    RaspberryNodes node = new RaspberryNodes();
                    node.setRaspberryUrl(eachNode.getRaspberryUrl());
                    RaspberryNodes nodeInfo = raspConn.selectFrom(node);
                    List<Sensors> sensors = new ArrayList<Sensors>();
                    sensors = senConn.selectFrom(nodeInfo.getRaspberryNode());

                    RaspberryLastObservation metanode = new RaspberryLastObservation();
                    List<SensorLastObservation> metaSensorLists = new ArrayList<SensorLastObservation>();
                    metanode.setRaspberryNode(nodeInfo.getRaspberryNode());
                    metanode.setNodeURL(nodeInfo.getRaspberryUrl());
                    for(Sensors eachSensor: sensors) {
                        SensorLastObservation metaSensor = new SensorLastObservation();
                        metaSensor.setSensor(eachSensor.getSensorId());
                        metaSensor.setName(eachSensor.getSensorName());
                        metaSensor.setType(eachSensor.getType());
                        metaSensor.setPollingInterval(eachSensor.getPollingFrequency());
                        metaSensor.setLatitude(eachSensor.getLatitude());
                        metaSensor.setLongitude(eachSensor.getLongitude());
                        metaSensor.setLastObservation(obsConn.selectLastObservation(nodeInfo.getRaspberryNode(), eachSensor.getSensorId()));
                        metaSensorLists.add(metaSensor);
                    }
                    metanode.setSensors(metaSensorLists);
                    addedNodes.add(metanode);
                }

                response.setRaspberryNodes(addedNodes);
                return response;
            }catch(SQLException ex){
                logger.error("SQLException: Exception thrown while fetching data from the database due to "+ ex.getMessage());
                return null;
            }
        }
        else
            return null;
    }
}
