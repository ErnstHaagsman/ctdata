package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.dbconnectors.BaseDatabaseConnector;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.UserSensorsConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
/**
 * Created by aditi on 15/11/15.
 */
public class UserSensorsConnectorTest {
    private DatabaseConnector dbConnector;
    private UserSensorsConnector userSensorConn;
    private String raspberryNodeUrlOne;
    private String raspberryNodeUrlTwo;
    private String raspberryNodeUrlThree;
    private String raspberryNodeUrlFour;
    private String raspberryNodeUrlFive;

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
            this.userSensorConn = new UserSensorsConnector(this.dbConnector);

        }catch (IOException ex){

        }catch (SQLException ex){

        }

        this.raspberryNodeUrlOne = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlTwo = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlThree = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlFour = UUID.randomUUID()+"./raspberry.net";
        this.raspberryNodeUrlFive = UUID.randomUUID()+"./raspberry.net";
    }

    @Test
    public void insertTest() throws SQLException{
        UserSensors userSensors = new UserSensors("root", this.raspberryNodeUrlOne);
        int i = this.userSensorConn.insertInto(userSensors);
        assertTrue(i==1);
    }

    @Test
    public void updateTest() throws SQLException{
        UserSensors userSensors = new UserSensors("root", this.raspberryNodeUrlTwo);
        int i = this.userSensorConn.insertInto(userSensors);
        i = this.userSensorConn.updateFrom(userSensors);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest() throws SQLException{
        UserSensors userSensors = new UserSensors("admin", this.raspberryNodeUrlThree);
        int i = this.userSensorConn.insertInto(userSensors);
        i = this.userSensorConn.deleteFrom(userSensors);
        assertTrue(i==1);
    }

    @Test
    public void selectAllTest() throws SQLException{
        List<UserSensors> list = new ArrayList<UserSensors>();

        UserSensors userSensors = new UserSensors("localadmin", this.raspberryNodeUrlOne);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors = new UserSensors("localroot", this.raspberryNodeUrlTwo);
        i = this.userSensorConn.insertInto(userSensors);

        list = this.userSensorConn.selectAll();
        assertTrue(list.size()>0);
    }

    @Test
    public void selectTest() throws SQLException{
        List<UserSensors> list = new ArrayList<UserSensors>();

        UserSensors userSensors = new UserSensors("chris", this.raspberryNodeUrlOne);
        int i = this.userSensorConn.insertInto(userSensors);
        userSensors = new UserSensors("chris", this.raspberryNodeUrlTwo);
        i = this.userSensorConn.insertInto(userSensors);

        list = this.userSensorConn.selectFrom("chris");
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
