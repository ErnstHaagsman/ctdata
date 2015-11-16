package net.ctdata.datanode.dataresources;

import java.util.UUID;

/**
 * Created by aditi on 15/11/15.
 */
public class UserSensors {

    private String userId;
    private UUID raspberryNode;

    public UserSensors() {
    }

    public UserSensors(String userId, UUID raspberryNode) {
        this.userId = userId;
        this.raspberryNode = raspberryNode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getRaspberryNode() {
        return raspberryNode;
    }

    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }
}
