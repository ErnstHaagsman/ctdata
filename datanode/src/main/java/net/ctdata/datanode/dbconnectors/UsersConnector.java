package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.Users;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify Users table
 */
public class UsersConnector{

    private String query;
    private DatabaseConnector dbConnector;
    static Logger logger = Logger.getLogger(UsersConnector.class);

    public UsersConnector(DatabaseConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    public int insertInto(Users user) throws SQLException{

        this.query = "INSERT INTO Users VALUES ('" + user.getUserId() + "', '" + user.getPassword() + "')";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.INSERT_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int updateFrom(Users user) throws SQLException{

        this.query = "UPDATE Users SET Password = '" + user.getPassword() + "' WHERE User_Id = '" + user.getUserId() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.UPDATE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int deleteFrom(Users user) throws SQLException{

        this.query = "DELETE FROM Users WHERE User_Id = '" + user.getUserId() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.DELETE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public List<Users> selectAll() throws SQLException{
        List<Users> list = new ArrayList<Users>();

        this.query = "SELECT * FROM Users";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

        if(result!=null) {
            while (result.next()) {
                Users user = new Users();
                user.setUserId(result.getString("User_Id"));
                user.setPassword(result.getString("Password"));
                list.add(user);
            }
        }
        return list;
    }

    public Users selectFrom(Users user) throws SQLException{

        Users returnUser = new Users();

        this.query = "SELECT * FROM Users WHERE User_Id = '" + user.getUserId() + "'";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null) {
            while (result.next()) {
                returnUser.setUserId(result.getString("User_Id"));
                returnUser.setPassword(result.getString("Password"));
            }
        }
        return returnUser;
    }

    public int count() throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Users";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null) {
            while (result.next()) {
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

    public int count(Users user) throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Users WHERE User_Id = '" + user.getUserId() + "'";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null) {
            while (result.next()) {
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

}
