package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.Listeners.ObservationListener;
import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.ObservationsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by aditi on 19/11/15.
 */
public class MyObservationsListener extends ObservationListener {

    static Logger logger = Logger.getLogger(MyAddNodeListener.class);
    private DatabaseConnector dbConnector;

    public MyObservationsListener(DatabaseConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(Observation message) {
        //insert Observation into the Observations table
        logger.info("OBSERVATION: Received Observation data message from the raspberry node " + message.getRaspberryNode() + " and the sensor Id " + message.getSensor());
        ObservationsConnector obsConn = new ObservationsConnector(this.dbConnector);
        Observations obsData = new Observations();
        obsData.setRaspberryNode(message.getRaspberryNode());
        obsData.setSensorId(message.getSensor());
        obsData.setObservationData(message.getObservation());
        obsData.setObservationTime(DateTimeConversions.convertDateTimeToString(message.getTime()));
        try {
            int i = obsConn.insertInto(obsData);
            if(i == DatanodeConstants.FAILURE)
                logger.error("OBSERVATION: Failed to add the observation for the received message");
            else
                logger.info("OBSERVATION: Successfully inserted data into the database for the received messgae from the raspberry node " +
                        message.getRaspberryNode() + " and the sensor Id " + message.getSensor());
        }catch (SQLException ex){
            logger.error("OBSERVATION: SQLException thrown while inserting data into the database due to "+ ex.getMessage());
        }
    }
}
