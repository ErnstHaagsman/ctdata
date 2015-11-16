package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.utility.DatanodeConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify Raspberry_Nodes table
 */
public class RaspberryNodesConnector extends AdvanceDatabaseConnector {
    private String query;

    public RaspberryNodesConnector(){
        super();
    }

    public int insertInto(RaspberryNodes node){
        setUp();

        this.query = "INSERT INTO Raspberry_Nodes VALUES ('" + node.getRaspberryNode() + "', '" + node.getRaspberryUrl() + "', '" + node.getGatewayId()
                +"')";

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

    public int updateFrom(RaspberryNodes node){
        setUp();

        this.query = "UPDATE Raspberry_Nodes SET Raspberry_Url = '" + node.getRaspberryUrl() +
                "', Gateway_Id = '" + node.getGatewayId() + "' WHERE Raspberry_Node = '" + node.getRaspberryNode() + "'";

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

    public int deleteFrom(RaspberryNodes node){
        setUp();

        this.query = "DELETE FROM Raspberry_Nodes WHERE Raspberry_Node = '" + node.getRaspberryNode() + "'";
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

    public List<RaspberryNodes> selectAll(){
        List<RaspberryNodes> list = new ArrayList<RaspberryNodes>();
        setUp();

        this.query = "SELECT * FROM Raspberry_Nodes";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                RaspberryNodes node = new RaspberryNodes();
                node.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                node.setRaspberryUrl(result.getString("Raspberry_Url"));
                node.setGatewayId(UUID.fromString(result.getString("Gateway_Id")));

                list.add(node);
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return list;
    }

    public RaspberryNodes selectFrom(RaspberryNodes node){

        RaspberryNodes returnNode = new RaspberryNodes();
        setUp();

        this.query = "SELECT * FROM Raspberry_Nodes WHERE Raspberry_Node = '" + node.getRaspberryNode() + "'";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                returnNode.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                returnNode.setRaspberryUrl(result.getString("Raspberry_Url"));
                returnNode.setGatewayId(UUID.fromString(result.getString("Gateway_Id")));
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return returnNode;
    }

    public int count(){

        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Raspberry_Nodes";

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

    public int count(RaspberryNodes node){
        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Raspberry_Nodes WHERE Raspberry_Node = '" + node.getRaspberryNode() + "'";
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
