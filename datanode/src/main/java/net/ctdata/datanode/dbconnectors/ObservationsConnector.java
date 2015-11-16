package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify Observations table
 */
public class ObservationsConnector extends AdvanceDatabaseConnector {
    private String query;

    public ObservationsConnector(){
        super();
    }

    public int insertInto(Observations obs){
        setUp();

        this.query = "INSERT INTO Observations VALUES ('" + obs.getRaspberryNode() + "', " + obs.getSensorId() + ", " + obs.getObservationData()
                + ", '" + obs.getObservationTime() + "', '" + obs.getAcknowledgementFlag() + "')";

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

    public int updateFrom(Observations obs){
        setUp();

        this.query = "UPDATE Observations SET Acknowledgement_Flag = '" + obs.getAcknowledgementFlag() +
                "' WHERE Raspberry_Node = "+ "'" + obs.getRaspberryNode() + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";

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

    public int deleteFrom(Observations obs){
        setUp();

        this.query = "DELETE FROM Observations WHERE Raspberry_Node = "+ "'" + obs.getRaspberryNode()
                            + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";

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

    public List<Observations> selectAll(){
        List<Observations> list = new ArrayList<Observations>();
        setUp();

        this.query = "SELECT * FROM Observations";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                Observations obs = new Observations();
                obs.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                obs.setSensorId(result.getInt("Sensor_Id"));
                obs.setObservationData(result.getDouble("Observation_Data"));
                obs.setObservationTime(DateTimeConversions.getSQLTimestampString(result.getTimestamp("Observation_Time")));
                obs.setAcknowledgementFlag(result.getString("Acknowledgement_Flag").toCharArray()[0]);

                list.add(obs);
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return list;
    }

    public Observations selectFrom(Observations obs){

        Observations returnObs = new Observations();
        setUp();

        this.query = "SELECT * FROM Observations WHERE Raspberry_Node = "+ "'" +
                        obs.getRaspberryNode() + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                returnObs.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                returnObs.setSensorId(result.getInt("Sensor_Id"));
                returnObs.setObservationData(result.getDouble("Observation_Data"));
                returnObs.setObservationTime(DateTimeConversions.getSQLTimestampString(result.getTimestamp("Observation_Time")));
                returnObs.setAcknowledgementFlag(result.getString("Acknowledgement_Flag").toCharArray()[0]);

            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return returnObs;
    }

    public int count(){

        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Observations";

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

    public int count(Observations obs){
        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Observations " +
                        "WHERE Raspberry_Node = "+ "'" + obs.getRaspberryNode() + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";

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
