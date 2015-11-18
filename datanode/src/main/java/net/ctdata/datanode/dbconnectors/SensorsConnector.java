package net.ctdata.datanode.dbconnectors;

import net.ctdata.datanode.dataresources.Sensors;
import net.ctdata.datanode.utility.DatanodeConstants;
import net.ctdata.datanode.utility.DateTimeConversions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by aditi on 14/11/15.
 * Utilise this class to connect to the database and modify Sensors table
 */
public class SensorsConnector extends AdvanceDatabaseConnector {
    private String query;

    public SensorsConnector(){
        super();
    }

    public int insertInto(Sensors sensor){
        setUp();

        this.query = "INSERT INTO Sensors VALUES ('" + sensor.getRaspberryNode() + "', " + sensor.getSensorId() + ", " + sensor.getPollingFrequency()
                        + ", '" + sensor.getType() + "', " + sensor.getLatitude() + ", " + sensor.getLongitude() + ", '" + sensor.getLastObservationTime()
                            + "', '" + sensor.getSensorName() + "')";

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

    public int updateFrom(Sensors sensor){
        setUp();

        this.query = "UPDATE Sensors SET Last_Observation_Time = '" + sensor.getLastObservationTime() +
                        "' WHERE Raspberry_Node = "+ "'" + sensor.getRaspberryNode() + "' AND Sensor_Id = " + sensor.getSensorId();

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

    public int deleteFrom(Sensors sensor){
        setUp();

        this.query = "DELETE FROM Sensors WHERE Raspberry_Node = "+ "'" + sensor.getRaspberryNode() + "' AND Sensor_Id = " + sensor.getSensorId();

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

    public List<Sensors> selectAll(){
        List<Sensors> list = new ArrayList<Sensors>();
        setUp();

        this.query = "SELECT * FROM Sensors";
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                Sensors sensor = new Sensors();
                sensor.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                sensor.setSensorId(result.getInt("Sensor_Id"));
                sensor.setSensorName(result.getString("Sensor_Name"));
                sensor.setType(result.getString("Type"));
                sensor.setPollingFrequency(result.getInt("Polling_Frequency"));
              //  Timestamp ts = result.getTimestamp("Last_Observation_Time");
              //  sensor.setLastObservationTime(DateTimeConversions.convertFromTimestamp(ts));
                sensor.setLastObservationTime(DateTimeConversions.getSQLTimestampString(result.getTimestamp("Last_Observation_Time")));
                sensor.setLongitude(result.getDouble("Longitude"));
                sensor.setLatitude(result.getDouble("Longitude"));

                list.add(sensor);
            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return list;
    }

    public Sensors selectFrom(Sensors sensor){

        Sensors returnSensor = new Sensors();
        setUp();

        this.query = "SELECT * FROM Sensors WHERE Raspberry_Node = "+ "'" + sensor.getRaspberryNode() + "' AND Sensor_Id = " + sensor.getSensorId();
        try{
            ResultSet result = (ResultSet) executeQuery(this.query, DatanodeConstants.SELECT_FLAG);

            while(result.next()){
                returnSensor.setRaspberryNode(UUID.fromString(result.getString("Raspberry_Node")));
                returnSensor.setSensorId(result.getInt("Sensor_Id"));
                returnSensor.setSensorName(result.getString("Sensor_Name"));
                returnSensor.setType(result.getString("Type"));
                returnSensor.setPollingFrequency(result.getInt("Polling_Frequency"));
                //Timestamp ts = result.getTimestamp("Last_Observation_Time");
                //returnSensor.setLastObservationTime(DateTimeConversions.convertFromTimestamp(ts));
                returnSensor.setLastObservationTime(DateTimeConversions.getSQLTimestampString(result.getTimestamp("Last_Observation_Time")));
                returnSensor.setLongitude(result.getDouble("Longitude"));
                returnSensor.setLatitude(result.getDouble("Longitude"));

            }
        }catch(SQLException ex){
            System.err.println("SQLException thrown due to "+ ex.getMessage());
        }

        tearDown();
        return returnSensor;
    }

    public int count(){

        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Sensors";

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

    public int count(Sensors sensor){
        setUp();

        this.query = "SELECT COUNT(*) AS Total FROM Sensors WHERE Raspberry_Node = "+ "'" + sensor.getRaspberryNode() + "' AND Sensor_Id = " + sensor.getSensorId();

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
