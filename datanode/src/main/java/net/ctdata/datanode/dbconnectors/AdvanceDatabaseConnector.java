package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.utility.DatanodeConstants;

import java.sql.SQLException;

/**
 * Created by aditi on 14/11/15.
 */
public class AdvanceDatabaseConnector extends BaseDatabaseConnector {

    public AdvanceDatabaseConnector(){
        super();
    }

    @Override
    void setUp() {
        try{
            establishConnection(DatanodeConstants.DBCONFIGPATH);
        }catch (SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }
    }

    @Override
    void tearDown() {
        try{
            closeConnection();
        }catch (SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }
    }
}
