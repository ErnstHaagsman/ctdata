package net.ctdata.datanode.dataresources;

/**
 * Created by aditi on 14/11/15.
 * This class is a Java Object of the database table Users
 */
public class Users {
    private String userId;
    private String password;

    public Users() {
    }

    public Users(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
