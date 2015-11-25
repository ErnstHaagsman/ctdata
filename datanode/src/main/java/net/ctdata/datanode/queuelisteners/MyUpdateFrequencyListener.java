package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.UpdateFrequency;
import net.ctdata.common.Queue.Listeners.UpdateFrequencyListener;
import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.SensorsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by aditi on 24/11/15.
 */
public class MyUpdateFrequencyListener extends UpdateFrequencyListener{

    static Logger logger = Logger.getLogger(MyUpdateFrequencyListener.class);
    private DatabaseConnector dbConnector;

    public MyUpdateFrequencyListener(DatabaseConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(UpdateFrequency message) {
        // update the Polling_Frequency column in Sensors table
        logger.debug("UPDATE_FREQUENCY: Received the message for raspberry node "+
                message.getRaspberryNode()+ " and sensor Id "+ message.getSensor());

        SensorsConnector sensorConn = new SensorsConnector(this.dbConnector);
        Sensors sensors = new Sensors();
        sensors.setRaspberryNode(message.getRaspberryNode());
        sensors.setSensorId(message.getSensor());
        sensors.setPollingFrequency(message.getPollingFrequency());

        try {
            int i = sensorConn.updateFrom(sensors);
            if(i == DatanodeConstants.FAILURE)
                logger.error("UPDATE_FREQUENCY: Failed to update the polling frequency of "+
                        message.getRaspberryNode()+ " and sensor Id "+ message.getSensor());
            else
                logger.debug("UPDATE_FREQUENCY: Successfully updated the polling frequency "+
                        message.getRaspberryNode()+ " and sensor Id "+ message.getSensor());
        }catch (SQLException ex){
            logger.error("UPDATE_FREQUENCY: SQLException thrown while updating data into the database due to "+ ex.getMessage());
        }
    }
}
