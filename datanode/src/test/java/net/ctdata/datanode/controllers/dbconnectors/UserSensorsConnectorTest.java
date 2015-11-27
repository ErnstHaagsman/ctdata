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
    private String raspberryNodeUrlOne;
    private String raspberryNodeUrlTwo;
    private String raspberryNodeUrlThree;
    private String raspberryNodeUrlFour;
    private String raspberryNodeUrlFive;

    @Before
    public void setUp(){
        this.userSensorConn = new UserSensorsConnector();
        this.raspberryNodeUrlOne = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlTwo = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlThree = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlFour = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlFive = UUID.randomUUID()+"./raspberry.net";
    }

    @Test
    public void insertTest(){
        UserSensors userSensors = new UserSensors("root", this.raspberryNodeUrlOne);
        int i = this.userSensorConn.insertInto(userSensors);
        assertTrue(i==1);
    }

    @Test
    public void updateTest(){
        UserSensors userSensors = new UserSensors("root", this.raspberryNodeUrlTwo);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors.setRaspberryUrl(this.raspberryNodeUrlThree);
        i = this.userSensorConn.updateFrom(userSensors, this.raspberryNodeUrlTwo);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest(){
        UserSensors userSensors = new UserSensors("admin", this.raspberryNodeUrlThree);
        int i = this.userSensorConn.insertInto(userSensors);
        i = this.userSensorConn.deleteFrom(userSensors);
        assertTrue(i==1);
    }

    @Test
    public void selectAllTest(){
        List<UserSensors> list = new ArrayList<UserSensors>();

        UserSensors userSensors = new UserSensors("localadmin", this.raspberryNodeUrlOne);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors = new UserSensors("localroot", this.raspberryNodeUrlTwo);
        i = this.userSensorConn.insertInto(userSensors);

        list = this.userSensorConn.selectAll();
        assertTrue(list.size()>0);
    }

    @Test
    public void selectTest(){
        List<UserSensors> list = new ArrayList<UserSensors>();

        UserSensors userSensors = new UserSensors("chris", this.raspberryNodeUrlOne);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors = new UserSensors("chris", this.raspberryNodeUrlTwo);
        i = this.userSensorConn.insertInto(userSensors);

        list = this.userSensorConn.selectFrom("chris");
        assertTrue(list.size()>0);
    }

}
