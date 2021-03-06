package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.configuration.DatanodeConfiguration;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by aditi on 14/11/15.
 * This class implements the interface DatabaseConnector for MYSQL database system
 */

public class BaseDatabaseConnector implements DatabaseConnector{

    private static Logger logger = Logger.getLogger(BaseDatabaseConnector.class);
    private Connection conn;
    private DatanodeConfiguration dbConfig;
    private Statement stmt;

    public BaseDatabaseConnector(DatanodeConfiguration dbConfig){
        this.dbConfig = dbConfig;
    }



    @Override
    public int establishConnection() throws SQLException {

            String url = "jdbc:mysql://" + dbConfig.getDatabaseHost()
                            + ":" + dbConfig.getDatabasePort() + "/" + dbConfig.getDatabaseSchema();

            logger.info("Attempting to connect to the database server at "+ url);

            this.conn = DriverManager.getConnection(url, dbConfig.getDatabaseUser(), dbConfig.getDatabasePswd());

            if(this.conn!=null) {
                logger.info("Database connection successfully established..");
                return DatanodeConstants.SUCCESS;
            }
            else {
                logger.error("Connection not yet established..");
                return DatanodeConstants.FAILURE;
            }
    }

    @Override
    public Object executeQuery(String query, char flag) throws SQLException {

        if(this.conn==null){
            logger.error("Database connection not yet established.. establish the connection first");
            return null;
        }
        else{
            this.stmt = this.conn.createStatement();

            logger.info("Attempting to execute the query '"+ query + "'");
            if(flag == DatanodeConstants.SELECT_FLAG){
                ResultSet result = stmt.executeQuery(query);
                return result;
            }
            else if((flag == DatanodeConstants.INSERT_FLAG) || (flag == DatanodeConstants.UPDATE_FLAG) || (flag == DatanodeConstants.DELETE_FLAG)){
                int count = stmt.executeUpdate(query);
                return new Integer(count);
            }
            else{
                logger.error("Invalid query type flag received.. check the query flag");
                return null;
            }
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if(this.stmt!=null)
            this.stmt.close();

        if(this.conn!=null)
            this.conn.close();

        logger.info("Database connection successfully closed!!");
    }

}
