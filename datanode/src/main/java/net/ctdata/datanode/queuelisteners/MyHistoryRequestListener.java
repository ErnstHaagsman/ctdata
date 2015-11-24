package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.HistoryRequest;
import net.ctdata.common.Messages.HistoryResponse;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.Listeners.HistoryRequestListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.ObservationsConnector;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditi on 19/11/15.
 */
public class MyHistoryRequestListener extends HistoryRequestListener {

    static Logger logger = Logger.getLogger(MyHistoryRequestListener.class);
    private DatabaseConnector dbConnector;
    private RabbitMqConnection queueConn;

    public MyHistoryRequestListener(DatabaseConnector dbConnector, RabbitMqConnection queueConn){
        this.dbConnector = dbConnector;
        this.queueConn = queueConn;
    }

    @Override
    public void HandleMessage(HistoryRequest message) {

        logger.info("HISTORY_REQUEST: Received history data request for the raspberry_node "+ message.getRaspberryNode()
                    + " and sensor Id "+ message.getSensor());
        HistoryResponse response = new HistoryResponse();
        response.setRequestId(message.getRequestID());
        response.setRaspberryNode(message.getRaspberryNode());
        response.setSensor(message.getSensor());

        List<Observations> list = new ArrayList<Observations>();
        Observations obs = new Observations();
        obs.setRaspberryNode(message.getRaspberryNode());
        obs.setSensorId(message.getSensor());
        ObservationsConnector obsConn = new ObservationsConnector(this.dbConnector);
        try{
            list = obsConn.selectBetween(obs, message.getTimePeriod());
            if(list!=null){
                if(list.size()>0){
                    logger.info("HISTORY_REQUEST: Fetched observation data successfully!!");
                    List<Observation> returnList = new ArrayList<Observation>();
                    for(Observations o : list){
                        Observation observation = new Observation();
                        observation.setRaspberryNode(o.getRaspberryNode());
                        observation.setSensor(o.getSensorId());
                        observation.setObservation(o.getObservationData());
                        observation.setTime(DateTimeConversions.convertStringToDateTime(o.getObservationTime()));
                        observation.setLatitude(o.getLatitude());
                        observation.setLongitude(o.getLongitude());
                        returnList.add(observation);
                    }
                    response.setObservations(returnList);
                    logger.info("HISTORY_RESPONSE: Sending response message..");
                    queueConn.SendMessage(response);
                }
            }
            else{
                logger.error("HISTORY_REQUEST: No history observation data fetched!!");
                response.setObservations(null);
                logger.info("HISTORY_RESPONSE: Sending response message with empty list..");
                queueConn.SendMessage(response);
            }

        }catch (SQLException ex){
            logger.error("HISTORY_REQUEST: SQLException caught while fecthing history data for the received request due to "+ ex.getMessage());
        }
    }
}
