package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.dbconnectors.SensorsConnector;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by aditi on 15/11/15.
 */
public class SensorConnectorTest {

    private SensorsConnector sensorsConn;
    private UUID raspberryNodeIdOne;
    private UUID raspberryNodeIdTwo;

    @Before
    public void setUp(){
        this.sensorsConn = new SensorsConnector();
        this.raspberryNodeIdOne = UUID.randomUUID();
        this.raspberryNodeIdTwo = UUID.randomUUID();
    }

    @Test
    public void insertTest(){


        Sensors sensor = new Sensors(this.raspberryNodeIdOne, 1, "Alpha", "Temperature", 1000,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        int i = this.sensorsConn.insertInto(sensor);
        assertTrue(i==1);
    }

    @Test
    public void updateTest(){

        Sensors sensor = new Sensors(this.raspberryNodeIdTwo, 1, "Alpha", "Temperature", 2000,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        int i = this.sensorsConn.insertInto(sensor);

        sensor.setLastObservationTime(DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())));

        i = this.sensorsConn.updateFrom(sensor);
        assertTrue(i==1);

    }

    @Test
    public void deleteTest(){
        Sensors sensor = new Sensors(this.raspberryNodeIdOne, 2, "Beta", "Temperature", 1500,
                DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), 37.3394 , -121.8938);

        int i = this.sensorsConn.insertInto(sensor);

        i = this.sensorsConn.deleteFrom(sensor);
        assertTrue(i==1);
    }

    @Test
    public void selectAll(){
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
}
