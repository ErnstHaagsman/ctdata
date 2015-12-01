package net.ctdata.datanode;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.datanode.configuration.CliOptions;
import net.ctdata.datanode.configuration.DatabaseConfiguration;
import net.ctdata.datanode.configuration.RabbitMQConfiguration;
import net.ctdata.datanode.dbconnectors.BaseDatabaseConnector;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.queuelisteners.*;
import org.apache.commons.cli.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
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
            logger.debug("Fetching the Command Line Options");
            CommandLineParser parser = new DefaultParser();
            Options options = CliOptions.getOptions();
            BasicConfigurator.configure();
            DatabaseConfiguration dbConfig = null;
            RabbitMQConfiguration queueConfig = null;

            try {
                CommandLine cmd = parser.parse(options, args);

                String dbfileName = cmd.getOptionValue(CliOptions.OPTIONS_DBCONFIG);
                File dbconfigFile = new File(dbfileName);

                ObjectMapper dbmapper = new MapperSingleton().getMapper();
                dbConfig = dbmapper.readValue(dbconfigFile, DatabaseConfiguration.class);

                String qfileName = cmd.getOptionValue(CliOptions.OPTIONS_QCONFIG);
                File qconfigFile = new File(qfileName);

                ObjectMapper queuemapper = new MapperSingleton().getMapper();
                queueConfig = queuemapper.readValue(qconfigFile, RabbitMQConfiguration.class);

            } catch (ParseException e) {
                logger.error("Could not parse CLI options: " + e.getMessage());
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("datanode", options);
                System.exit(-1);
            } catch (IOException e) {
                logger.error("Could not read configuration file: " + e.getMessage());
                System.exit(-1);
            }

            // Initialising the database connection
            logger.debug("Setting up the database connection..");
            dbConnector = new BaseDatabaseConnector(dbConfig);

            // Establishing the database connection
            try {
                dbConnector.establishConnection();
            }catch (SQLException ex){
                logger.error("SQLException thrown while connecting to the database due to " + ex.getMessage());
                logger.info("Stoping server..");
                System.exit(-1);
            }

            logger.debug("Database connection established");

            //Establishing the RabbitMQ connection
            logger.debug("Setting up the RabbitMQ connection..");
            conn = new RabbitMqConnection(queueConfig.getRabbitmqUri());
            logger.debug("RabbitMQ connection established");

            // Register ADD_NODE message listener
            logger.debug("Registering ADD_NODE message listener..");
            conn.RegisterListener(new MyAddNodeListener(conn, dbConnector));

            // Register CONNECT message listener
            logger.debug("Registering CONNECT message listener..");
            conn.RegisterListener(new MyConnectListener(conn, dbConnector));

            // Register HISTORY_REQUEST message listener
            logger.debug("Registering HISTORY_REQUEST message listener..");
            conn.RegisterListener(new MyHistoryRequestListener(dbConnector, conn));

            // Register UPDATE_FREQUENCY message listener
            logger.debug("Registering UPDATE_FREQUENCY message listener");
            conn.RegisterListener(new MyUpdateFrequencyListener(dbConnector));

            // Register REQUEST_ADDED_NODES message listener
            logger.debug("Registering REQUEST_ADDED_NODES message listener");
            conn.RegisterListener(new MyRequestAddedNodesListener(conn, dbConnector));

            // Register REQUEST_NODES message listener
            logger.debug("Registering REQUEST_NODES message listener");
            conn.RegisterListener(new MyRequestNodesListener(conn, dbConnector));

        }
}
