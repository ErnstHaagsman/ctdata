package net.ctdata.datanode.queuelisteners;

import net.ctdata.common.Messages.Connect;
import net.ctdata.common.Messages.RequestNodes;
import net.ctdata.common.Queue.Listeners.RequestNodesListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.RaspberryNodesConnector;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditi on 25/11/15.
 */
public class MyRequestNodesListener extends RequestNodesListener {

    static Logger logger = Logger.getLogger(MyRequestNodesListener.class);
    private DatabaseConnector dbConnector;
    private RabbitMqConnection queueConn;

    public MyRequestNodesListener(RabbitMqConnection queueConn, DatabaseConnector dbConnector){
        super();
        this.queueConn = queueConn;
        this.dbConnector = dbConnector;
    }

    @Override
    public void HandleMessage(RequestNodes message) {

        logger.debug("REQUEST_NODES: message received from gateway Id "+ message.getGatewayId());
        RaspberryNodesConnector raspConn = new RaspberryNodesConnector(this.dbConnector);
        List<RaspberryNodes> list = new ArrayList<RaspberryNodes>();

        try{
            list = raspConn.selectAll();
            if(list!=null){
                if(list.size()>0){
                    for(RaspberryNodes node: list){
                        Connect connect = new Connect();
                        connect.setGatewayId(message.getGatewayId());
                        connect.setNodeURL(node.getRaspberryUrl());
                        logger.debug("CONNECT: Sending connect message for node url "+ node.getRaspberryUrl());
                        this.queueConn.SendMessage(connect);
                    }
                }
                else{
                    logger.debug("No raspberry node is currently added to the system");
                }
            }
            else{
                logger.debug("No data is fetched for the added nodes");
            }
        }catch (SQLException ex){
            logger.error("REQUEST_NODES: SQLException thrown while fetching data due to "+ ex.getMessage());
        }
    }
}
