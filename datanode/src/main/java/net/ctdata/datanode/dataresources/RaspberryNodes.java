package net.ctdata.datanode.dataresources;

import java.util.UUID;

/**
 * Created by aditi on 14/11/15.
 * This class is a Java Object of the database table Raspberry_Nodes
 */
public class RaspberryNodes {
    private UUID raspberryNode;
    private String raspberryUrl;
    private UUID gatewayId;

    public RaspberryNodes() {
    }

    public RaspberryNodes(UUID raspberryNode, String raspberryUrl, UUID gatewayId) {
        this.raspberryNode = raspberryNode;
        this.raspberryUrl = raspberryUrl;
        this.gatewayId = gatewayId;
    }

    public UUID getRaspberryNode() {
        return raspberryNode;
    }

    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }

    public String getRaspberryUrl() {
        return raspberryUrl;
    }

    public void setRaspberryUrl(String raspberryUrl) {
        this.raspberryUrl = raspberryUrl;
    }

    public UUID getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(UUID gatewayId) {
        this.gatewayId = gatewayId;
    }
}
