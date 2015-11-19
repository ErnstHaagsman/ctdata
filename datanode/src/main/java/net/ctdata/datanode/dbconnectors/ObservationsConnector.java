package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.Observations;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by aditi on 15/11/15.
 * Utilise this class to connect to the database and modify Observations table
 */
public class ObservationsConnector{
    private String query;
    private DatabaseConnector dbConnector;
    static Logger logger = Logger.getLogger(ObservationsConnector.class);


    public ObservationsConnector(DatabaseConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    public int insertInto(Observations obs) throws SQLException{

        this.query = "INSERT INTO Observations VALUES ('" + obs.getRaspberryNode() + "', " + obs.getSensorId() + ", " + obs.getObservationData()
                + ", '" + obs.getObservationTime() + "', '" + obs.getAcknowledgementFlag() + "')";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.INSERT_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int updateFrom(Observations obs) throws SQLException{

        this.query = "UPDATE Observations SET Acknowledgement_Flag = '" + obs.getAcknowledgementFlag() +
                "' WHERE Raspberry_Node = "+ "'" + obs.getRaspberryNode() + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.UPDATE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int deleteFrom(Observations obs) throws SQLException{

        this.query = "DELETE FROM Observations WHERE Raspberry_Node = "+ "'" + obs.getRaspberryNode()
                            + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.DELETE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public List<Observations> selectAll() throws SQLException{
        List<Observations> list = new ArrayList<Observations>();

        this.query = "SELECT * FROM Observations";
        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

        if(result!=null){
            while(result.next()){
                Observations obs = new Observations();
                obs.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                obs.setSensorId(result.getInt("Sensor_Id"));
                obs.setObservationData(result.getDouble("Observation_Data"));
                obs.setObservationTime(DateTimeConversions.getSQLTimestampString(result.getTimestamp("Observation_Time")));
                obs.setAcknowledgementFlag(result.getString("Acknowledgement_Flag").toCharArray()[0]);

                list.add(obs);
            }
        }
        return list;
    }

    public Observations selectFrom(Observations obs) throws SQLException{

        Observations returnObs = new Observations();

        this.query = "SELECT * FROM Observations WHERE Raspberry_Node = "+ "'" +
                        obs.getRaspberryNode() + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                returnObs.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                returnObs.setSensorId(result.getInt("Sensor_Id"));
                returnObs.setObservationData(result.getDouble("Observation_Data"));
                returnObs.setObservationTime(DateTimeConversions.getSQLTimestampString(result.getTimestamp("Observation_Time")));
                returnObs.setAcknowledgementFlag(result.getString("Acknowledgement_Flag").toCharArray()[0]);

            }
        }
        return returnObs;
    }

    public int count() throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Observations";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

    public int count(Observations obs) throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Observations " +
                        "WHERE Raspberry_Node = "+ "'" + obs.getRaspberryNode() + "' AND Sensor_Id = " + obs.getSensorId() + " AND Observation_Time = '" + obs.getObservationTime() + "'";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

}
