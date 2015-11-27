package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.dbconnectors.ObservationsConnector;
import net.ctdata.datanode.utility.DatanodeConstants;
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
public class ObservationsConnectorTest {

    private ObservationsConnector obsConn;
    private UUID raspberryNodeIdOne;
    private UUID raspberryNodeIdTwo;

    @Before
    public void setUp(){
        this.obsConn = new ObservationsConnector();
        this.raspberryNodeIdOne = UUID.randomUUID();
        this.raspberryNodeIdTwo = UUID.randomUUID();
    }

    @Test
    public void insertTest(){
        Observations obs = new Observations(this.raspberryNodeIdOne, 1, 35.65, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK);

        int i = this.obsConn.insertInto(obs);
        assertTrue(i==1);
    }

    @Test
    public void updateTest(){
        Observations obs = new Observations(this.raspberryNodeIdTwo, 1, 67.98, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.NACK);

        int i = this.obsConn.insertInto(obs);

        obs.setAcknowledgementFlag(DatanodeConstants.ACK);
        i = this.obsConn.updateFrom(obs);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest(){
        Observations obs = new Observations(this.raspberryNodeIdTwo, 2, 45.98, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK);

        int i = this.obsConn.insertInto(obs);
        i= this.obsConn.deleteFrom(obs);
        assertTrue(i==1);
    }

    @Test
    public void selectTest(){
        List<Observations> list = new ArrayList<Observations>();

        Observations obs = new Observations(this.raspberryNodeIdOne, 2, 106.78, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK);
        int i = this.obsConn.insertInto(obs);

        obs = new Observations(this.raspberryNodeIdTwo, 3, 12.89, DateTimeConversions.getSQLTimestampString(new Timestamp(new Date().getTime())), DatanodeConstants.ACK);
        i = this.obsConn.insertInto(obs);

        list = this.obsConn.selectAll();
        assertTrue(list.size()>0);
    }
}
