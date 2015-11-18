package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.utility.DatanodeConstants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by aditi on 14/11/15.
 * This class implements the interface DatabaseConnector for MYSQL database system
 */

abstract public class BaseDatabaseConnector implements DatabaseConnector{

    private Connection conn;
    private Properties properties;
    private Statement stmt;

    public BaseDatabaseConnector(){
        this.properties = new Properties();
    }

    /*
    * Used to set database properties
    * See: http://www.mkyong.com/java/java-properties-file-examples/
    */
    private int setDbProperties(String configFilePath){

        try{

            InputStream input;
            input = new FileInputStream(configFilePath);
            this.properties.load(input);
            return DatanodeConstants.SUCCESS;

        }catch(FileNotFoundException ex){
            System.err.println("FileNotFoundException thrown due to "+ ex.getMessage());
            return DatanodeConstants.FAILURE;

        }catch(IOException ex){
            System.err.println("IOException thrown due to "+ ex.getMessage());
            return DatanodeConstants.FAILURE;
        }
    }

    @Override
    public int establishConnection(String configFilePath) throws SQLException {

     //   if(this.setDbProperties(configFilePath)== DatanodeConstants.SUCCESS){

//            String url = "jdbc:mysql://" + this.properties.getProperty("databasehost")
//                            + ":" + this.properties.getProperty("databaseport") + "/" + this.properties.getProperty("databaseschema");
//
//            this.conn = DriverManager.getConnection(url, this.properties.getProperty("databaseuser"), this.properties.getProperty("databasepswd"));

            String url = "jdbc:mysql://localhost:3306/cdata";

            this.conn = DriverManager.getConnection(url, "root", "admin123");

            if(this.conn!=null) {
                System.out.println("Database connection successfully established..");
                return DatanodeConstants.SUCCESS;
            }
            else {
                System.err.println("Failed to establish database connection..");
                return DatanodeConstants.FAILURE;
            }
      //  }
     //   else
      //      return DatanodeConstants.FAILURE;
    }

    @Override
    public Object executeQuery(String query, char flag) throws SQLException {

        if(this.conn==null){
            System.err.println("Database connection not yet established.. establish the connection first");
            return null;
        }
        else{
            this.stmt = this.conn.createStatement();

            if(flag == DatanodeConstants.SELECT_FLAG){
                ResultSet result = stmt.executeQuery(query);
                return result;
            }
            else if((flag == DatanodeConstants.INSERT_FLAG) || (flag == DatanodeConstants.UPDATE_FLAG) || (flag == DatanodeConstants.DELETE_FLAG)){
                int count = stmt.executeUpdate(query);
                return new Integer(count);
            }
            else{
                System.err.println("Invalid query type");
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

        System.out.println("Database connection successfully closed!!");
    }

    abstract void setUp();

    abstract void tearDown();

}
