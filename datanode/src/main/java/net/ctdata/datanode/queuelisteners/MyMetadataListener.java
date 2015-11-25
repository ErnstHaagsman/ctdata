package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Messages.Partial.SensorMetadata;
import net.ctdata.common.Queue.Listeners.MetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.RaspberryNodesConnector;
import net.ctdata.datanode.dbconnectors.SensorsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by aditi on 21/11/15.
 */
public class MyMetadataListener extends MetadataListener {

    static Logger logger = Logger.getLogger(MyMetadataListener.class);
    private DatabaseConnector dbConnector;
    private RabbitMqConnection queueConn;

    public MyMetadataListener(RabbitMqConnection queueConn, DatabaseConnector dbConnector){
        super();
        this.queueConn = queueConn;
        this.dbConnector = dbConnector;
    }

    public MyMetadataListener(String nodeUrl, RabbitMqConnection queueConn, DatabaseConnector dbConnector){
        super(nodeUrl);
        this.queueConn = queueConn;
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(Metadata message) {
        // Tasks to be done
        // Task #1: update raspberry_node in Raspberry_Nodes table
        // Task #2: insert metadata into Sensors table
        // Task #3: Register observation listener for each sensor
        logger.debug("METADATA: Received message for raspberry node Id "+ message.getRaspberryNode() +" and raspberry url "+ message.getNodeUrl());

        // Task #1: update raspberry_node in Raspberry_Nodes table
        RaspberryNodesConnector raspConn = new RaspberryNodesConnector(dbConnector);
        RaspberryNodes raspNode = new RaspberryNodes();
        raspNode.setRaspberryNode(message.getRaspberryNode());
        raspNode.setRaspberryUrl(message.getNodeUrl());
        try {
            int i = raspConn.updateFrom(raspNode);
            if(i == DatanodeConstants.FAILURE)
                logger.error("METADATA: Failed to record raspberry node Id for the raspberry url "+ message.getNodeUrl());
            else
                logger.debug("METADATA: Successfully inserted raspberry node Id for the raspberry url "+ message.getNodeUrl());
        }catch (SQLException ex){
            logger.error("METADATA: SQLException thrown while inserting data into the database due to "+ ex.getMessage());
        }

        // Task #2: insert metadata into Sensors table
        if(message.getSensors()!=null){
            SensorsConnector sensorConn = new SensorsConnector(dbConnector);
            for(SensorMetadata s : message.getSensors()){
                Sensors sensor = new Sensors();
                sensor.setRaspberryNode(message.getRaspberryNode());
                sensor.setSensorId(s.getSensor());
                sensor.setSensorName(s.getName());
                sensor.setType(s.getType());
                sensor.setPollingFrequency(s.getPollingInterval());
                sensor.setLastObservationTime(DateTimeConversions.convertDateTimeToString(s.getLastObservation()));
                sensor.setLatitude(s.getLatitude());
                sensor.setLongitude(s.getLongitude());
                try {
                    int i = sensorConn.insertInto(sensor);
                    if(i == DatanodeConstants.FAILURE) {
                        logger.error("METADATA: Failed to record metadata for raspberry url "+ message.getNodeUrl());
                        break;
                    }
                    else {
                        logger.debug("METADATA: Successfully inserted sensor metadata for sensor Id "+ sensor.getSensorId() +" and raspberry url "+ message.getNodeUrl());
                        // Task #3: Register observation listener for each sensor
                        logger.debug("Registering Observations listener for raspberry node " + message.getRaspberryNode()
                            + " and sensor Id "+ s.getSensor());
                        this.queueConn.RegisterListener(new MyObservationsListener(this.dbConnector,
                                this.queueConn, message.getRaspberryNode(), s.getSensor()));
                        continue;
                    }
                }catch (SQLException ex){
                    logger.error("METADATA: SQLException thrown while inserting data into the database due to "+ ex.getMessage());
                    break;
                }
            }
        }
        else{
            logger.error("METADATA: Received message has no metadata of the raspberry node with url "+ message.getNodeUrl());
        }
    }
}
