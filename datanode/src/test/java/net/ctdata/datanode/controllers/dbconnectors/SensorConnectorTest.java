package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.dbconnectors.BaseDatabaseConnector;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.SensorsConnector;
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
public class SensorConnectorTest {
    private DatabaseConnector dbConnector;
    private SensorsConnector sensorsConn;
    private UUID raspberryNodeIdOne;
    private UUID raspberryNodeIdTwo;

    @Before
    public void setUp(){
        try {
            // fetching the database connection properties
            InputStream input = this.getClass().getResourceAsStream("dbconfig.properties");
            Properties properties = new Properties();
            properties.load(input);
            // Initialising the database connection
            this.dbConnector = new BaseDatabaseConnector(properties);
            this.dbConnector.establishConnection();
            this.sensorsConn = new SensorsConnector(this.dbConnector);

        }catch (IOException ex){

        }catch (SQLException ex){

        }

        this.raspberryNodeIdOne = UUID.randomUUID();
        this.raspberryNodeIdTwo = UUID.randomUUID();
    }

    @Test
    public void insertTest() throws SQLException{

        Sensors sensor = new Sensors(this.raspberryNodeIdOne, 1, "Alpha", "Temperature", 1000,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        int i = this.sensorsConn.insertInto(sensor);
        assertTrue(i==1);
    }

    @Test
    public void updateTest() throws SQLException{

        Sensors sensor = new Sensors(this.raspberryNodeIdTwo, 1, "Alpha", "Temperature", 2000,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        int i = this.sensorsConn.insertInto(sensor);

        sensor.setLastObservationTime(DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())));

        i = this.sensorsConn.updateFrom(sensor);
        assertTrue(i==1);

    }

    @Test
    public void deleteTest() throws SQLException{
        Sensors sensor = new Sensors(this.raspberryNodeIdOne, 2, "Beta", "Temperature", 1500,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        int i = this.sensorsConn.insertInto(sensor);

        i = this.sensorsConn.deleteFrom(sensor);
        assertTrue(i==1);
    }

    @Test
    public void selectAll() throws SQLException{
        Sensors sensor = new Sensors(this.raspberryNodeIdOne, 3, "Gamma", "Temperature", 1500,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        int i = this.sensorsConn.insertInto(sensor);

        sensor = new Sensors(this.raspberryNodeIdTwo, 2, "Yeta", "Temperature", 1800,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        i = this.sensorsConn.insertInto(sensor);

        List<Sensors> list = new ArrayList<Sensors>();
        list = this.sensorsConn.selectAll();

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
