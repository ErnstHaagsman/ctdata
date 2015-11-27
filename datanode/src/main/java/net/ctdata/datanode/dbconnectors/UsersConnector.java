package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.Users;
import net.ctdata.datanode.utility.DatanodeConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify Users table
 */
public class UsersConnector extends AdvanceDatabaseConnector{

    private String query;

    public UsersConnector(){
        super();
    }

    public int insertInto(Users user){
        setUp();

        this.query = "INSERT INTO Users VALUES ('" + user.getUserId() + "', '" + user.getPassword() + "')";

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

    public int updateFrom(Users user){
        setUp();

        this.query = "UPDATE Users SET Password = '" + user.getPassword() + "' WHERE User_Id = '" + user.getUserId() + "'";

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

    public int deleteFrom(Users user){
        setUp();

        this.query = "DELETE FROM Users WHERE User_Id = '" + user.getUserId() + "'";
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

    public List<Users> selectAll(){
        List<Users> list = new ArrayList<Users>();
        setUp();

        this.query = "SELECT * FROM Users";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                Users user = new Users();
                user.setUserId(result.getString("User_Id"));
                user.setPassword(result.getString("Password"));
                list.add(user);
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return list;
    }

    public Users selectFrom(Users user){

        Users returnUser = new Users();
        setUp();

        this.query = "SELECT * FROM Users WHERE User_Id = '" + user.getUserId() + "'";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                returnUser.setUserId(result.getString("User_Id"));
                returnUser.setPassword(result.getString("Password"));
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return returnUser;
    }

    public int count(){

        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Users";

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

    public int count(Users user){
        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Users WHERE User_Id = '" + user.getUserId() + "'";
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
