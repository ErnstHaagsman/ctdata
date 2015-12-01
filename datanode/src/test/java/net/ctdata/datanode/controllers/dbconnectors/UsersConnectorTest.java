/*
package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.DatanodeManager;
import net.ctdata.datanode.dataresources.Users;
import net.ctdata.datanode.dbconnectors.BaseDatabaseConnector;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.UsersConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
*/
/**
 * Created by aditi on 15/11/15.
 *//*

public class UsersConnectorTest {
    private DatabaseConnector dbConnector;
    private UsersConnector userConn;

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
            this.userConn = new UsersConnector(this.dbConnector);

        }catch (IOException ex){

        }catch (SQLException ex){

        }
    }

    @Test
    public void insertTest() throws SQLException{
        Users user = new Users("root", "admin");
        int i = this.userConn.insertInto(user);
        userConn.deleteFrom(user);
        assertTrue(i==1);
    }

    @Test
    public void updateTest() throws SQLException{
        Users user = new Users("admin", "admin123");
        int i = this.userConn.insertInto(user);

        user.setPassword("password");
        i = this.userConn.updateFrom(user);
        userConn.deleteFrom(user);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest() throws SQLException{
        Users user = new Users("chris", "williams");
        int i = this.userConn.insertInto(user);
        i = this.userConn.deleteFrom(user);
        assertTrue(i==1);
    }

    @Test
    public void selectTest() throws SQLException{
        List<Users> list = new ArrayList<Users>();

        Users user1 = new Users("ross", "ross123");
        int i = this.userConn.insertInto(user1);
        Users user2 = new Users("michael", "pswd123");
        i = this.userConn.insertInto(user2);

        list = this.userConn.selectAll();
        userConn.deleteFrom(user1);
        userConn.deleteFrom(user2);
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
*/
