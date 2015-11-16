package net.ctdata.datanode.controllers.dbconnectors;

import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.dbconnectors.RaspberryNodesConnector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
/**
 * Created by aditi on 15/11/15.
 */
public class RaspberryNodesConnectorTest {

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
        this.raspConn = new RaspberryNodesConnector();
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
    public void insertTest(){
        RaspberryNodes node = new RaspberryNodes(this.raspberryNodeIdOne, this.raspberryNodeOneUrl, this.gatewayIdOne);

        int i = raspConn.insertInto(node);
        assertTrue(i==1);
    }

    @Test
    public void uodateTest(){
        RaspberryNodes node = new RaspberryNodes(this.raspberryNodeIdTwo, this.raspberryNodeOneUrl, this.gatewayIdOne);
        int i = raspConn.insertInto(node);

        node.setRaspberryUrl(this.raspberryNodeTwoUrl);
        node.setGatewayId(this.gatewayIdTwo);
        i = this.raspConn.updateFrom(node);

        assertTrue(i==1);
    }

    @Test
    public void deleteTest(){
        RaspberryNodes node = new RaspberryNodes(this.raspberryNodeIdThree, this.raspberryNodeThreeUrl, this.gatewayIdTwo);
        int i = raspConn.insertInto(node);
        i = this.raspConn.deleteFrom(node);
        assertTrue(i==1);
    }

    @Test
    public void selectAll(){
        List<RaspberryNodes> list = new ArrayList<RaspberryNodes>();

        RaspberryNodes node = new RaspberryNodes(this.raspberryNodeIdFour, this.raspberryNodeFourUrl, this.gatewayIdTwo);
        int i = raspConn.insertInto(node);
        node = new RaspberryNodes(this.raspberryNodeIdFive, this.raspberryNodeFiveUrl, this.gatewayIdOne);
        i = raspConn.insertInto(node);

        list = this.raspConn.selectAll();
        assertTrue(list.size()>0);
    }
}
