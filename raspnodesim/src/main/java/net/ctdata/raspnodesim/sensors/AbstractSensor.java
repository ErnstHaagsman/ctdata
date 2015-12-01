package net.ctdata.raspnodesim.sensors;

import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Messages.Partial.SensorMetadata;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

public abstract class AbstractSensor implements Sensor {

    DateTime nextObservationTime = DateTime.now();
    int pollingInterval;
    int number;
    double latitude;
    double longitude;
    String type;
    String name;

    public AbstractSensor(){
    }

    public AbstractSensor(int pollingInterval, int number){
        this.pollingInterval = pollingInterval;
        this.number = number;
    }

    public SensorMetadata getMetadata(){
        SensorMetadata sensorMetadata = new SensorMetadata();
        sensorMetadata.setType(getType());
        sensorMetadata.setPollingInterval(getPollingInterval());
        sensorMetadata.setLongitude(getLongitude());
        sensorMetadata.setLatitude(getLatitude());
        sensorMetadata.setName(getName());
        sensorMetadata.setSensor(getNumber());
        return sensorMetadata;
    }

    @Override
    public DateTime getNextObservationTime() {
        return nextObservationTime;
    }

    @Override
    public void advanceObservationTime() {
        nextObservationTime = nextObservationTime.plusMillis(pollingInterval);
    }

    @Override
    public int getPollingInterval() {
        return pollingInterval;
    }

    @Override
    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    @Override
    public int getNumber(){
        return number;
    }

    @Override
    public void setNumber(int number){
        this.number = number;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Observation getObservation(UUID raspNodeId) {
        Observation observation = new Observation();
        observation.setTime(DateTime.now());
        observation.setObservation(getData());
        observation.setSensor(getNumber());
        observation.setRaspberryNode(raspNodeId);
        observation.setLongitude(getLongitude());
        observation.setLatitude(getLatitude());
        return observation;
    }
}
