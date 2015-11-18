package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.dataresources.Users;
import net.ctdata.datanode.dbconnectors.UsersConnector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
/**
 * Created by aditi on 15/11/15.
 */
public class UsersConnectorTest {

    private UsersConnector userConn;

    @Before
    public void setUp(){
        this.userConn = new UsersConnector();
    }

    @Test
    public void insertTest(){
        Users user = new Users("root", "admin");
        int i = this.userConn.insertInto(user);
        assertTrue(i==1);
    }

    @Test
    public void updateTest(){
        Users user = new Users("admin", "admin123");
        int i = this.userConn.insertInto(user);

        user.setPassword("password");
        i = this.userConn.updateFrom(user);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest(){
        Users user = new Users("chris", "williams");
        int i = this.userConn.insertInto(user);
        i = this.userConn.deleteFrom(user);
        assertTrue(i==1);
    }

    @Test
    public void selectTest(){
        List<Users> list = new ArrayList<Users>();

        Users user = new Users("ross", "ross123");
        int i = this.userConn.insertInto(user);
        user = new Users("michael", "pswd123");
        i = this.userConn.insertInto(user);

        list = this.userConn.selectAll();
        assertTrue(list.size()>0);
    }
}
