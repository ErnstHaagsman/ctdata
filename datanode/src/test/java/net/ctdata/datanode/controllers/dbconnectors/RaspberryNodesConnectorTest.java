/*
package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.DatanodeManager;
import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.dbconnectors.BaseDatabaseConnector;
import net.ctdata.datanode.dbconnectors.DatabaseConnector;
import net.ctdata.datanode.dbconnectors.RaspberryNodesConnector;
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
*/
/**
 * Created by aditi on 15/11/15.
 *//*

public class RaspberryNodesConnectorTest {
    private DatabaseConnector dbConnector;
    private RaspberryNodesConnector raspConn;
    private UUID raspberryNodeIdOne;
    private UUID raspberryNodeIdTwo;
    private UUID raspberryNodeIdThree;
    private UUID raspberryNodeIdFour;
    private UUID raspberryNodeIdFive;
    private String raspberryNodeOneUrl;
    private String raspberryNodeTwoUrl;
    private String raspberryNodeThreeUrl;
    private String raspberryNodeFourUrl;
    private String raspberryNodeFiveUrl;
    private UUID gatewayIdOne;
    private UUID gatewayIdTwo;

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
            this.raspConn = new RaspberryNodesConnector(this.dbConnector);

        }catch (IOException ex){

        }catch (SQLException ex){

        }

        this.raspberryNodeIdOne = UUID.randomUUID();
        this.raspberryNodeIdTwo = UUID.randomUUID();
        this.raspberryNodeIdThree = UUID.randomUUID();
        this.raspberryNodeIdFour = UUID.randomUUID();
        this.raspberryNodeIdFive = UUID.randomUUID();
        this.raspberryNodeOneUrl = this.raspberryNodeIdOne.toString() + "/raspberry.net";
        this.raspberryNodeTwoUrl = this.raspberryNodeIdTwo.toString() + "/raspberry.net";
        this.raspberryNodeThreeUrl = this.raspberryNodeIdThree.toString() + "/raspberry.net";
        this.raspberryNodeFourUrl = this.raspberryNodeIdFour.toString() + "/raspberry.net";
        this.raspberryNodeFiveUrl = this.raspberryNodeIdFive.toString() + "/raspberry.net";
        this.gatewayIdOne = UUID.randomUUID();
        this.gatewayIdTwo = UUID.randomUUID();
    }

    @Test
    public void insertTest() throws SQLException{
        RaspberryNodes node = new RaspberryNodes(this.raspberryNodeIdOne, this.raspberryNodeOneUrl, this.gatewayIdOne);

        int i = raspConn.insertInto(node);
        raspConn.deleteFrom(node);
        assertTrue(i==1);

    }

    @Test
    public void updateTest() throws SQLException{
        RaspberryNodes node = new RaspberryNodes(this.raspberryNodeIdTwo, this.raspberryNodeOneUrl, this.gatewayIdOne);
        int i = raspConn.insertInto(node);
        node.setRaspberryNode(this.raspberryNodeIdOne);
        i = this.raspConn.updateFrom(node);
        raspConn.deleteFrom(node);
        assertTrue(i==1);
    }

    @Test
    public void deleteTest() throws SQLException{
        RaspberryNodes node = new RaspberryNodes(this.raspberryNodeIdThree, this.raspberryNodeThreeUrl, this.gatewayIdTwo);
        int i = raspConn.insertInto(node);
        i = this.raspConn.deleteFrom(node);
        assertTrue(i==1);
    }

    @Test
    public void selectAll() throws SQLException{
        List<RaspberryNodes> list = new ArrayList<RaspberryNodes>();

        RaspberryNodes node1 = new RaspberryNodes(this.raspberryNodeIdFour, this.raspberryNodeFourUrl, this.gatewayIdTwo);
        int i = raspConn.insertInto(node1);
        RaspberryNodes node2 = new RaspberryNodes(this.raspberryNodeIdFive, this.raspberryNodeFiveUrl, this.gatewayIdOne);
        i = raspConn.insertInto(node2);

        list = this.raspConn.selectAll();
        raspConn.deleteFrom(node1);
        raspConn.deleteFrom(node2);
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
