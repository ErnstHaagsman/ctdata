package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.dbconnectors.UserSensorsConnector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
/**
 * Created by aditi on 15/11/15.
 */
public class UserSensorsConnectorTest {

    private UserSensorsConnector userSensorConn;
    private UUID raspberryNodeIdOne;
    private UUID raspberryNodeIdTwo;
    private UUID raspberryNodeIdThree;
    private UUID raspberryNodeIdFour;
    private UUID raspberryNodeIdFive;

    @Before
    public void setUp(){
        this.userSensorConn = new UserSensorsConnector();
        this.raspberryNodeIdOne = UUID.randomUUID();
        this.raspberryNodeIdTwo = UUID.randomUUID();
        this.raspberryNodeIdThree = UUID.randomUUID();
        this.raspberryNodeIdFour = UUID.randomUUID();
        this.raspberryNodeIdFive = UUID.randomUUID();
    }

    @Test
    public void insertTest(){
        UserSensors userSensors = new UserSensors("root", this.raspberryNodeIdOne);
        int i = this.userSensorConn.insertInto(userSensors);
        assertTrue(i==1);
    }

    @Test
    public void updateTest(){
        UserSensors userSensors = new UserSensors("root", this.raspberryNodeIdTwo);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors.setRaspberryNode(this.raspberryNodeIdThree);
        i = this.userSensorConn.updateFrom(userSensors, this.raspberryNodeIdTwo);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest(){
        UserSensors userSensors = new UserSensors("admin", this.raspberryNodeIdThree);
        int i = this.userSensorConn.insertInto(userSensors);
        i = this.userSensorConn.deleteFrom(userSensors);
        assertTrue(i==1);
    }

    @Test
    public void selectAllTest(){
        List<UserSensors> list = new ArrayList<UserSensors>();

        UserSensors userSensors = new UserSensors("localadmin", this.raspberryNodeIdOne);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors = new UserSensors("localroot", this.raspberryNodeIdTwo);
        i = this.userSensorConn.insertInto(userSensors);

        list = this.userSensorConn.selectAll();
        assertTrue(list.size()>0);
    }

    @Test
    public void selectTest(){
        List<UserSensors> list = new ArrayList<UserSensors>();

        UserSensors userSensors = new UserSensors("chris", this.raspberryNodeIdOne);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors = new UserSensors("chris", this.raspberryNodeIdTwo);
        i = this.userSensorConn.insertInto(userSensors);

        list = this.userSensorConn.selectFrom("chris");
        assertTrue(list.size()>0);
    }
}
