package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.Connect;
import net.ctdata.common.Queue.Listeners.ConnectListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.RaspberryNodesConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by aditi on 21/11/15.
 */
public class MyConnectListener extends ConnectListener {
    static Logger logger = Logger.getLogger(MyConnectListener.class);
    private RabbitMqConnection queueConn;
    private DatabaseConnector dbConnector;

    public MyConnectListener(RabbitMqConnection queueConn, DatabaseConnector dbConnector){
        this.queueConn = queueConn;
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(Connect message) {
        // insert the connect message information into the Raspberry_Nodes table to be used by the Orchestration server
        // for determining free gateway Ids.
        logger.info("CONNECT: Received connect message for raspberry url "+ message.getNodeURL());
        RaspberryNodesConnector raspConn = new RaspberryNodesConnector(this.dbConnector);
        RaspberryNodes raspNode = new RaspberryNodes();
        raspNode.setGatewayId(message.getGatewayId());
        raspNode.setRaspberryUrl(message.getNodeURL());
        raspNode.setRaspberryNode(null);
        try {
            int i = raspConn.insertInto(raspNode);
            if(i == DatanodeConstants.FAILURE)
                logger.error("CONNECT: Failed to record connection data for the raspberry url "+ message.getNodeURL());
            else
                logger.info("CONNECT: Successfully inserted connection data for the raspberry url "+ message.getNodeURL());
        }catch (SQLException ex){
            logger.error("CONNECT: SQLException thrown while inserting data into the database due to "+ ex.getMessage());
        }

        // Upon reception of CONNECT message, register MyMetadataListener to listen to METADATA for the corresponding <raspberry_url>
        // this is preferred to avoid listening to all METADATA messages on network
        logger.info("Registering METADATA message listener for raspberry url "+ message.getNodeURL());
        this.queueConn.RegisterListener(new MyMetadataListener(message.getNodeURL(), this.dbConnector));
    }
}
