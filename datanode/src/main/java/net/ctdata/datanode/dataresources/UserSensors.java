package net.ctdata.datanode.dataresources;

/**
 * Created by aditi on 15/11/15.
 */
public class UserSensors {

    private String userId;
    private String raspberryUrl;
    private char connectionFlag;

    public UserSensors() {
    }

    public UserSensors(String userId, String raspberryUrl) {
        this.userId = userId;
        this.raspberryUrl = raspberryUrl;
        this.connectionFlag = 'N';
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

    public char getConnectionFlag() {
        return connectionFlag;
    }

    public void setConnectionFlag(char connectionFlag) {
        this.connectionFlag = connectionFlag;
    }
}
