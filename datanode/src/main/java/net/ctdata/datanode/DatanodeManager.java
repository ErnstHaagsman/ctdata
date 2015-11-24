package net.ctdata.datanode;

import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Messages.HistoryRequest;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.dbconnectors.BaseDatabaseConnector;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.queuelisteners.*;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeoutException;



/**
 * Created by aditi on 14/11/15.
 * See for adding properties file in the project http://stackoverflow.com/questions/18717038/adding-resources-in-intellij-for-java-project
 * See for Log4j Initialisation: http://howtodoinjava.com/2013/04/08/how-to-configure-log4j-using-properties-file/,
 * http://www.petrikainulainen.net/programming/gradle/getting-started-with-gradle-dependency-management/
 */
public class DatanodeManager {

        static Logger logger = Logger.getLogger(DatanodeManager.class);
        static DatabaseConnector dbConnector;
        static RabbitMqConnection conn;

        public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException
        {
            // setting up log4j configurations
            InputStream input = DatanodeManager.class.getResourceAsStream("log4j.properties");
            Properties properties = new Properties();
            properties.load(input);
            PropertyConfigurator.configure(properties);
            logger.info("Database manager server started successfully!!");

            // fetching the database connection properties
            logger.info("Setting up the database connection..");
            input = DatanodeManager.class.getResourceAsStream("dbconfig.properties");
            properties = new Properties();
            properties.load(input);

            // Initialising the database connection
            dbConnector = new BaseDatabaseConnector(properties);

            // Establishing the database connection - The server will keep trying to connect to the database until a connection is setup
            int success;
            do{
                try {
                    success = dbConnector.establishConnection();
                }catch (SQLException ex){
                    logger.error("SQLException thrown while connecting to the database due to " + ex.getMessage());
                    success = DatanodeConstants.FAILURE;
                }
            }while(success == DatanodeConstants.FAILURE);

            //fetching the rabbitMQ connection properties
            logger.info("Setting up the RabbitMQ connection..");
            input = DatanodeManager.class.getResourceAsStream("rabbitmq.properties");
            properties = new Properties();
            properties.load(input);

            //Establishing the RabbitMQ connection
            conn = new RabbitMqConnection(properties.getProperty("rabbitmqUrl"));

            // Register ADD_NODE message listener
            logger.info("Registering ADD_NODE message listener..");
            conn.RegisterListener(new MyAddNodeListener(conn, dbConnector));

            // Register CONNECT message listener
            logger.info("Registering CONNECT message listener..");
            conn.RegisterListener(new MyConnectListener(conn, dbConnector));

            // Register UPDATE_FREQUENCY message listener
            logger.info("Registering UPDATE_FREQUENCY message listener");
            conn.RegisterListener(new MyUpdateFrequencyListener(dbConnector));



            // Register OBSERVATION message listener
            logger.info("Registering OBSERVATION message listener..");
            conn.RegisterListener(new MyObservationsListener(dbConnector, conn));

            // Register HISTORY_REQUEST message listener
            logger.info("Registering HISTORY_REQUEST message listener..");
            conn.RegisterListener(new MyHistoryRequestListener(dbConnector, conn));

            // Simulating ADD_NODE message for testing
            AddNode addNode = new AddNode();
            addNode.setUserId("admin");
            addNode.setNodeURL(UUID.randomUUID()+"/raspberry.net");
            conn.SendMessage(addNode);

            // Simulating OBSERVATION message for testing
            Observation obs = new Observation();
            obs.setRaspberryNode(UUID.randomUUID());
            obs.setSensor(1);
            obs.setObservation(115.45);
            obs.setTime(DateTime.now());
            obs.setLongitude(37.3394 );
            obs.setLatitude(-121.8938);
            conn.SendMessage(obs);

            // Simulating HISTORY_REQUEST message for testing
            HistoryRequest msg = new HistoryRequest();



        }
}
