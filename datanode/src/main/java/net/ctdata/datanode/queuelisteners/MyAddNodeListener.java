package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Queue.Listeners.AddNodeListener;
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

    public MyAddNodeListener(DatabaseConnector dbConnector){
        super();
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(AddNode message) {
        // insert AddNode data into User_Sensors table
        logger.info("ADD_NODE: Received message from the user "+ message.getUserId());
        UserSensorsConnector userSensorsConnector = new UserSensorsConnector(this.dbConnector);
        UserSensors userSensors = new UserSensors(message.getUserId(), message.getNodeURL());
        try {
            int i = userSensorsConnector.insertInto(userSensors);
            if(i == DatanodeConstants.FAILURE)
                logger.error("ADD_NODE: Failed to add the data for the received message");
            else
                logger.info("ADD_NODE: Successfully inserted data into the database for the received messgae from the user "+ message.getUserId());
        }catch (SQLException ex){
            logger.error("ADD_NODE: SQLException thrown while inserting data into the database due to "+ ex.getMessage());
        }
    }
}
