package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.RaspberryNodes;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify Raspberry_Nodes table
 */
public class RaspberryNodesConnector{
    private String query;
    private DatabaseConnector dbConnector;
    static Logger logger = Logger.getLogger(RaspberryNodesConnector.class);

    public RaspberryNodesConnector(DatabaseConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    public int insertInto(RaspberryNodes node) throws SQLException{

        this.query = "INSERT INTO Raspberry_Nodes VALUES ('" + node.getRaspberryNode() + "', '" + node.getRaspberryUrl() + "', '" + node.getGatewayId()
                +"')";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.INSERT_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int updateFrom(RaspberryNodes node) throws SQLException{

        this.query = "UPDATE Raspberry_Nodes SET Raspberry_Node = '" + node.getRaspberryNode() + "' WHERE Raspberry_Url = '" + node.getRaspberryUrl() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.UPDATE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int deleteFrom(RaspberryNodes node) throws SQLException{

        this.query = "DELETE FROM Raspberry_Nodes WHERE Raspberry_Url = '" + node.getRaspberryUrl() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.DELETE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public List<RaspberryNodes> selectAll() throws SQLException{
        List<RaspberryNodes> list = new ArrayList<RaspberryNodes>();

        this.query = "SELECT * FROM Raspberry_Nodes";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                RaspberryNodes node = new RaspberryNodes();
                node.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                node.setRaspberryUrl(result.getString("Raspberry_Url"));
                node.setGatewayId(UUID.fromString(result.getString("Gateway_Id")));

                list.add(node);
            }
        }
        return list;
    }

    public RaspberryNodes selectFrom(RaspberryNodes node) throws SQLException{

        RaspberryNodes returnNode = new RaspberryNodes();

        this.query = "SELECT * FROM Raspberry_Nodes WHERE Raspberry_Url = '" + node.getRaspberryUrl() + "'";
        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                returnNode.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                returnNode.setRaspberryUrl(result.getString("Raspberry_Url"));
                returnNode.setGatewayId(UUID.fromString(result.getString("Gateway_Id")));
            }
        }
        return returnNode;
    }

    public int count() throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Raspberry_Nodes";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

    public int count(RaspberryNodes node) throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Raspberry_Nodes WHERE Raspberry_Url = '" + node.getRaspberryUrl() + "'";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

}
