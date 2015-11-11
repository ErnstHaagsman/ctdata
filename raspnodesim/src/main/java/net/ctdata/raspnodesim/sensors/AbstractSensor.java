package net.ctdata.raspnodesim.sensors;

import org.joda.time.DateTime;
import org.joda.time.Period;

public abstract class AbstractSensor implements Sensor {

    DateTime nextObservationTime = DateTime.now();
    Period pollingInterval;
    int number;

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
}
