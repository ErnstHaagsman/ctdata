package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.Confirmation;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.Listeners.ObservationListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.ObservationsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by aditi on 19/11/15.
 */
public class MyObservationsListener extends ObservationListener {

    static Logger logger = Logger.getLogger(MyAddNodeListener.class);
    private DatabaseConnector dbConnector;
    private RabbitMqConnection queueConn;

    public MyObservationsListener(DatabaseConnector dbConnector, RabbitMqConnection queueConn){
        this.dbConnector = dbConnector;
        this.queueConn = queueConn;
    }

    public MyObservationsListener(DatabaseConnector dbConnector, RabbitMqConnection queueConn,
                                  UUID raspberryNodeId, int sensor){
        super(raspberryNodeId, sensor);
        this.dbConnector = dbConnector;
        this.queueConn = queueConn;
    }

    @Override
    public void HandleMessage(Observation message) {
        // Tasks to be done
        // Task #1: insert Observation into the Observations table
        // Task #2: acknowledge storing the observation data

        //insert Observation into the Observations table
        logger.debug("OBSERVATION: Received Observation data message from the raspberry node " + message.getRaspberryNode() + " and the sensor Id " + message.getSensor());
        ObservationsConnector obsConn = new ObservationsConnector(this.dbConnector);
        Observations obsData = new Observations();
        obsData.setRaspberryNode(message.getRaspberryNode());
        obsData.setSensorId(message.getSensor());
        obsData.setObservationData(message.getObservation());
        obsData.setObservationTime(DateTimeConversions.convertDateTimeToString(message.getTime()));
        obsData.setLatitude(message.getLatitude());
        obsData.setLongitude(message.getLongitude());

        try {
            int i = obsConn.insertInto(obsData);
            if(i == DatanodeConstants.FAILURE)
                logger.error("OBSERVATION: Failed to add the observation for the received message");
            else {
                logger.debug("OBSERVATION: Successfully inserted data into the database for the received messgae from the raspberry node " +
                        message.getRaspberryNode() + " and the sensor Id " + message.getSensor());

                // acknowledge storing the observation data
                obsData.setAcknowledgementFlag(DatanodeConstants.ACK);
                i = obsConn.updateFrom(obsData);
                if(i == DatanodeConstants.FAILURE)
                    logger.error("OBSERVATION: Failed to acknowledge storing the observation data");
                else{
                    Confirmation confirmation = new Confirmation();
                    confirmation.setRaspberryNode(message.getRaspberryNode());
                    confirmation.setSensor(message.getSensor());
                    confirmation.setTime(DateTime.now());
                    logger.debug("OBSERVATION: Sending ACK message for the received message");
                    queueConn.SendMessage(confirmation);
                }
            }
        }catch (SQLException ex){
            logger.error("OBSERVATION: SQLException thrown while inserting data into the database due to "+ ex.getMessage());
        }
    }
}
