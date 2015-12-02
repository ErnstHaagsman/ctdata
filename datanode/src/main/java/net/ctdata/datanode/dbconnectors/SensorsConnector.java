package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.utility.DatanodeConstants;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by aditi on 14/11/15.
 * Utilise this class to connect to the database and modify Sensors table
 */
public class SensorsConnector{
    private String query;
    private DatabaseConnector dbConnector;
    static Logger logger = Logger.getLogger(SensorsConnector.class);

    public SensorsConnector(DatabaseConnector dbConnector){

        this.dbConnector = dbConnector;
    }

    public int insertInto(Sensors sensor) throws SQLException{

        this.query = "INSERT INTO Sensors VALUES ('" + sensor.getRaspberryNode() + "', " + sensor.getSensorId() + ", " + sensor.getPollingFrequency()
                        + ", '" + sensor.getType() + "', " + sensor.getLatitude() + ", " + sensor.getLongitude() + ", '" + sensor.getSensorName() + "')";

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.INSERT_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int updateFrom(Sensors sensor) throws SQLException{

        this.query = "UPDATE Sensors SET Polling_Frequency = '" + sensor.getPollingFrequency() +
                        "' WHERE Raspberry_Node = "+ "'" + sensor.getRaspberryNode() + "' AND Sensor_Id = " + sensor.getSensorId();

        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.UPDATE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public int deleteFrom(Sensors sensor) throws SQLException{

        this.query = "DELETE FROM Sensors WHERE Raspberry_Node = "+ "'" + sensor.getRaspberryNode() + "' AND Sensor_Id = " + sensor.getSensorId();


        Integer count = (Integer) dbConnector.executeQuery(this.query, DatanodeConstants.DELETE_FLAG);
        if(count != null)
            return count.intValue();
        else{
            return DatanodeConstants.FAILURE;
        }
    }

    public List<Sensors> selectAll() throws SQLException{
        List<Sensors> list = new ArrayList<Sensors>();

        this.query = "SELECT * FROM Sensors";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

        if(result!=null){
            while(result.next()){
                Sensors sensor = new Sensors();
                sensor.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                sensor.setSensorId(result.getInt("Sensor_Id"));
                sensor.setSensorName(result.getString("Sensor_Name"));
                sensor.setType(result.getString("Type"));
                sensor.setPollingFrequency(result.getInt("Polling_Frequency"));
                sensor.setLongitude(result.getDouble("Longitude"));
                sensor.setLatitude(result.getDouble("Longitude"));

                list.add(sensor);
            }
        }
        return list;
    }

    public List<Sensors> selectFrom(UUID nodeId) throws SQLException{

        List<Sensors> list = new ArrayList<Sensors>();

        this.query = "SELECT * FROM Sensors WHERE Raspberry_Node = '" + nodeId + "'";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

        if(result!=null){
            while(result.next()){
                Sensors sensor = new Sensors();
                sensor.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                sensor.setSensorId(result.getInt("Sensor_Id"));
                sensor.setSensorName(result.getString("Sensor_Name"));
                sensor.setType(result.getString("Type"));
                sensor.setPollingFrequency(result.getInt("Polling_Frequency"));
                sensor.setLongitude(result.getDouble("Longitude"));
                sensor.setLatitude(result.getDouble("Latitude"));

                list.add(sensor);
            }
        }
        return list;
    }

    public int count() throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Sensors";

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){

                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

    public int count(Sensors sensor) throws SQLException{

        this.query = "SELECT COUNT(*) AS Total FROM Sensors WHERE Raspberry_Node = "+ "'" + sensor.getRaspberryNode() + "' AND Sensor_Id = " + sensor.getSensorId();

        ResultSet result = (ResultSet) dbConnector.executeQuery(this.query, DatanodeConstants.SELECT_FLAG);
        if(result!=null){
            while(result.next()){
                return result.getInt("Total");
            }
        }
        return DatanodeConstants.FAILURE;
    }

}
