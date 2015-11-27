package net.ctdata.datanode.controllers.dbconnectors;


import net.ctdata.datanode.DatanodeManager;
import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.dbconnectors.BaseDatabaseConnector;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.ObservationsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by aditi on 15/11/15.
 */
public class ObservationsConnectorTest {
    private DatabaseConnector dbConnector;
    private ObservationsConnector obsConn;
    private UUID raspberryNodeIdOne;
    private UUID raspberryNodeIdTwo;

    @Before
    public void setUp(){
        try {
            // fetching the database connection properties
            InputStream input = DatanodeManager.class.getResourceAsStream("dbconfig.properties");
            Properties properties = new Properties();
            properties.load(input);
            // Initialising the database connection
            this.dbConnector = new BaseDatabaseConnector(properties);
            this.dbConnector.establishConnection();
            this.obsConn = new ObservationsConnector(this.dbConnector);

        }catch (IOException ex){

        }catch (SQLException ex){

        }

        this.raspberryNodeIdOne = UUID.randomUUID();
        this.raspberryNodeIdTwo = UUID.randomUUID();
    }

    @Test
    public void insertTest() throws SQLException{
        Observations obs = new Observations(this.raspberryNodeIdOne, 1, 35.65, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK, 37.3394 , -121.8938);

        int i = this.obsConn.insertInto(obs);
        obsConn.deleteFrom(obs);
        assertTrue(i==1);
    }

    @Test
    public void updateTest() throws SQLException{
        Observations obs = new Observations(this.raspberryNodeIdTwo, 1, 67.98, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.NACK, 37.3394 , -121.8938);

        int i = this.obsConn.insertInto(obs);

        obs.setAcknowledgementFlag(DatanodeConstants.ACK);
        i = this.obsConn.updateFrom(obs);
        obsConn.deleteFrom(obs);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest() throws SQLException{
        Observations obs = new Observations(this.raspberryNodeIdTwo, 2, 45.98, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK, 37.3394 , -121.8938);

        int i = this.obsConn.insertInto(obs);
        i= this.obsConn.deleteFrom(obs);
        assertTrue(i==1);
    }

    @Test
    public void selectTest() throws SQLException{
        List<Observations> list = new ArrayList<Observations>();

        Observations obs1 = new Observations(this.raspberryNodeIdOne, 2, 106.78, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK, 37.3394 , -121.8938);
        int i = this.obsConn.insertInto(obs1);

        Observations obs2 = new Observations(this.raspberryNodeIdTwo, 3, 12.89, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK, 37.3394 , -121.8938);
        i = this.obsConn.insertInto(obs2);

        list = this.obsConn.selectAll();
        obsConn.deleteFrom(obs1);
        obsConn.deleteFrom(obs2);
        assertTrue(list.size()>0);
    }

    @After
    public void TearDown(){
        try{
            this.dbConnector.closeConnection();
        }catch (SQLException ex){

        }
    }
}
