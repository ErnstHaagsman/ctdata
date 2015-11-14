package net.ctdata.raspnodesim.sensors;

import net.ctdata.common.Messages.Observation;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

public abstract class AbstractSensor implements Sensor {

    DateTime nextObservationTime = DateTime.now();
    Period pollingInterval;
    int number;
    double latitude;
    double longitude;

    public AbstractSensor(Period pollingInterval, int number){
        this.pollingInterval = pollingInterval;
        this.number = number;
    }

    @Override
    public DateTime getNextObservationTime() {
        return nextObservationTime;
    }

    @Override
    public void advanceObservationTime() {
        nextObservationTime = nextObservationTime.plus(pollingInterval);
    }

    @Override
    public Period getPollingInterval() {
        return pollingInterval;
    }

    @Override
    public void setPollingInterval(Period pollingInterval) {
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
