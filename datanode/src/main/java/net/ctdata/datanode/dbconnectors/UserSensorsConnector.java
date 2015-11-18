package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.utility.DatanodeConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify User_Sensors table
 */
public class UserSensorsConnector extends AdvanceDatabaseConnector {
    private String query;

    public UserSensorsConnector(){
        super();
    }

    public int insertInto(UserSensors userSensors){
        setUp();

        this.query = "INSERT INTO User_Sensors VALUES ('" + userSensors.getUserId() + "', '" + userSensors.getRaspberryUrl() + "')";

        try{
            Integer count = (Integer) executeQuery(this.query, DatanodeConstants.INSERT_FLAG);
            tearDown();
            return count.intValue();
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return DatanodeConstants.FAILURE;
    }

    public int updateFrom(UserSensors userSensors, String oldRaspberryUrl){
        setUp();

        this.query = "UPDATE User_Sensors SET Raspberry_Url = '" + userSensors.getRaspberryUrl() + "' WHERE User_Id = '" + userSensors.getUserId() + "'" +
                        "AND Raspberry_Url = '" + oldRaspberryUrl + "'";

        try{
            Integer count = (Integer) executeQuery(this.query, DatanodeConstants.UPDATE_FLAG);
            tearDown();
            return count.intValue();
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return DatanodeConstants.FAILURE;
    }

    public int deleteFrom(UserSensors userSensors){
        setUp();

        this.query = "DELETE FROM User_Sensors WHERE User_Id = '" + userSensors.getUserId() + "'" +
                        "AND Raspberry_Url = '" + userSensors.getRaspberryUrl() + "'";
        try{
            Integer count = (Integer) executeQuery(this.query, DatanodeConstants.DELETE_FLAG);
            tearDown();
            return count.intValue();
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return DatanodeConstants.FAILURE;
    }

    public List<UserSensors> selectAll(){
        List<UserSensors> list = new ArrayList<UserSensors>();
        setUp();

        this.query = "SELECT * FROM User_Sensors";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                UserSensors userSensors = new UserSensors();
                userSensors.setUserId(result.getString("User_Id"));
                userSensors.setRaspberryUrl(result.getString("Raspberry_Url"));
                list.add(userSensors);
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return list;
    }

    public List<UserSensors> selectFrom(String uId){
        List<UserSensors> list = new ArrayList<UserSensors>();
        setUp();

        this.query = "SELECT * FROM User_Sensors WHERE user_Id = '" + uId + "'";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                UserSensors userSensors = new UserSensors();
                userSensors.setUserId(result.getString("User_Id"));
                userSensors.setRaspberryUrl(result.getString("Raspberry_Url"));
                list.add(userSensors);
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return list;
    }

    public int count(){

        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM User_Sensors";

        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
            while(result.next()){
                tearDown();
                return result.getInt("Total");
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return DatanodeConstants.FAILURE;
    }

    public int count(UserSensors userSensors){
        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM User_Sensors WHERE User_Id = '" + userSensors.getUserId() + "'";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
            while(result.next()){
                tearDown();
                return result.getInt("Total");
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return DatanodeConstants.FAILURE;
    }

}
