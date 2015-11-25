package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.AddedNodesMetadata;
import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Messages.Partial.SensorMetadata;
import net.ctdata.common.Messages.RequestAddedNodes;
import net.ctdata.common.Queue.Listeners.RequestAddedNodesListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.RaspberryNodesConnector;
import net.ctdata.datanode.dbconnectors.SensorsConnector;
import net.ctdata.datanode.dbconnectors.UserSensorsConnector;
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

        logger.info("REQUEST_ADDED_NODES: Received " + message.getRequestId() +" request from user "+ message.getUserId()
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
            if(list.size()>=1){
                response = getResponse(list);
                response.setRequestId(message.getRequestId());
                logger.info("REQUEST_ADDED_NODES: Sending the response message for request Id "+ response.getRequestId());
                conn.SendMessage(response);
            }
            else{
                logger.info("REQUEST_ADDED_NODES: No conected sensors for the user "+ message.getUserId());
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
                List<Metadata> metaAddedNodes = new ArrayList<Metadata>();

                for(UserSensors eachNode: list) {
                    RaspberryNodes node = new RaspberryNodes();
                    node.setRaspberryUrl(eachNode.getRaspberryUrl());
                    RaspberryNodes nodeInfo = raspConn.selectFrom(node);
                    List<Sensors> sensors = new ArrayList<Sensors>();
                    sensors = senConn.selectFrom(nodeInfo.getRaspberryNode());

                    Metadata metanode = new Metadata();
                    List<SensorMetadata> metaSensorLists = new ArrayList<SensorMetadata>();
                    metanode.setRaspberryNode(nodeInfo.getRaspberryNode());
                    metanode.setNodeUrl(nodeInfo.getRaspberryUrl());
                    for(Sensors eachSensor: sensors) {
                        SensorMetadata metaSensor = new SensorMetadata();
                        metaSensor.setSensor(eachSensor.getSensorId());
                        metaSensor.setName(eachSensor.getSensorName());
                        metaSensor.setType(eachSensor.getType());
                        metaSensor.setPollingInterval(eachSensor.getPollingFrequency());
                        metaSensor.setLatitude(eachSensor.getLatitude());
                        metaSensor.setLongitude(eachSensor.getLongitude());
                        metaSensorLists.add(metaSensor);
                    }
                    metanode.setSensors(metaSensorLists);
                    metaAddedNodes.add(metanode);
                }

                response.setRaspberryNodes(metaAddedNodes);
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
