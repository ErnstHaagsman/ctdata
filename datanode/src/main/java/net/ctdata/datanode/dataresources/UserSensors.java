package net.ctdata.datanode.dataresources;

/**
 * Created by aditi on 15/11/15.
 */
public class UserSensors {

    private String userId;
    private String raspberryUrl;

    public UserSensors() {
    }

    public UserSensors(String userId, String raspberryUrl) {
        this.userId = userId;
        this.raspberryUrl = raspberryUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRaspberryUrl() {
        return raspberryUrl;
    }

    public void setRaspberryUrl(String raspberryUrl) {
        this.raspberryUrl = raspberryUrl;
    }
}
