package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.UserSensors;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify User_Sensors table
 */
public class UserSensorsConnector {
    private String query;
    private DatabaseConnector dbConnector;
    static Logger logger = Logger.getLogger(UserSensorsConnector.class);

    public UserSensorsConnector(DatabaseConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    public int insertInto(UserSensors userSensors) throws SQLException{

        this.query = "INSERT INTO User_Sensors VALUES ('" + userSensors.getUserId() + "', '" + userSensors.getRaspberryUrl() + "'," +
                       "'" + userSensors.getConnectionFlag() + "')";
        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.INSERT_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int updateFrom(UserSensors userSensors) throws SQLException{

        this.query = "UPDATE User_Sensors SET Connection_Flag = '" + userSensors.getConnectionFlag() + "' WHERE User_Id = '" + userSensors.getUserId() + "'" +
                        "AND Raspberry_Url = '" + userSensors.getRaspberryUrl() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.UPDATE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }

    }

    public int deleteFrom(UserSensors userSensors) throws SQLException{

        this.query = "DELETE FROM User_Sensors WHERE User_Id = '" + userSensors.getUserId() + "'" +
                        "AND Raspberry_Url = '" + userSensors.getRaspberryUrl() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.DELETE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }

    }

    public List<UserSensors> selectAll() throws SQLException{
        List<UserSensors> list = new ArrayList<UserSensors>();

        this.query = "SELECT * FROM User_Sensors WHERE Connection_Flag = 'Y'";
        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

        if(result != null){
            while(result.next()){
                UserSensors userSensors = new UserSensors();
                userSensors.setUserId(result.getString("User_Id"));
                userSensors.setRaspberryUrl(result.getString("Raspberry_Url"));
                list.add(userSensors);
            }
        }
        return list;
    }

    public List<UserSensors> selectFrom(String uId) throws SQLException{
        List<UserSensors> list = new ArrayList<UserSensors>();

        this.query = "SELECT * FROM User_Sensors WHERE user_Id = '" + uId + "'" +
                " AND Connection_Flag = 'Y'";
        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

        if(result != null){
            while(result.next()){
                UserSensors userSensors = new UserSensors();
                userSensors.setUserId(result.getString("User_Id"));
                userSensors.setRaspberryUrl(result.getString("Raspberry_Url"));
                list.add(userSensors);
            }
        }
        return list;
    }

    public int count() throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM User_Sensors";
        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

    public int count(UserSensors userSensors) throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM User_Sensors WHERE User_Id = '" + userSensors.getUserId() + "'";
        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

}
