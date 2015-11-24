package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Messages.Metadata;
import net.ctdata.common.Queue.Listeners.AddNodeListener;
import net.ctdata.common.Queue.Listeners.MetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.UserSensorsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by aditi on 18/11/15.
 */
public class MyAddNodeListener extends AddNodeListener {

    static Logger logger = Logger.getLogger(MyAddNodeListener.class);
    private DatabaseConnector dbConnector;
    private RabbitMqConnection queueConn;

    public MyAddNodeListener(RabbitMqConnection queueConn, DatabaseConnector dbConnector){
        super();
        this.queueConn = queueConn;
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(AddNode message) {
        // Tasks to be done
        // Task #1: insert AddNode data into User_Sensors table
        // Task #2: update the connection flag in User_Sensors table

        // Task #1: insert AddNode data into User_Sensors table
        logger.info("ADD_NODE: Received message from the user "+ message.getUserId());
        final UserSensorsConnector userSensorsConnector = new UserSensorsConnector(this.dbConnector);
        final UserSensors userSensors = new UserSensors(message.getUserId(), message.getNodeURL()); // connection flag is by default 'N' as the connection is not yet established
        try {
            int i = userSensorsConnector.insertInto(userSensors);
            if(i == DatanodeConstants.FAILURE)
                logger.error("ADD_NODE: Failed to add the data for the received message");
            else
                logger.info("ADD_NODE: Successfully inserted data into the database for the received messgae from the user "+ message.getUserId());
        }catch (SQLException ex){
            logger.error("ADD_NODE: SQLException thrown while inserting data into the database due to "+ ex.getMessage());
        }

        // Task #2: update the connection flag in User_Sensors table (METADATA message is also a kind of
        // ACK message for ADD_NODE)
        this.queueConn.RegisterListener(new MetadataListener(message.getNodeURL()) {
            @Override
            public void HandleMessage(Metadata message) {
                if(message.getNodeUrl().equalsIgnoreCase(userSensors.getRaspberryUrl())){
                    userSensors.setConnectionFlag('Y');
                    try {
                        int j = userSensorsConnector.updateFrom(userSensors);
                        if(j == DatanodeConstants.FAILURE)
                            logger.error("ADD_NODE - METADATA: Failed to update the connection flag for raspberry url "+ message.getNodeUrl());
                        else
                            logger.info("ADD_NODE - METADATA: Successfully updated the connection flag for raspberry url "+ message.getNodeUrl());
                    }catch (SQLException ex){
                        logger.error("ADD_NODE - METADATA: SQLException thrown while updating connection flag due to "+ ex.getMessage());
                    }
                }
            }
        });
    }
}
