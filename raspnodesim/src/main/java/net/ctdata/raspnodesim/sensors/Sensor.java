package net.ctdata.raspnodesim.sensors;

import org.joda.time.DateTime;
import org.joda.time.Period;

public interface Sensor {
    double getData();
    DateTime getNextObservationTime();
    void advanceObservationTime();
    Period getPollingInterval();
    void setPollingInterval(Period pollingInterval);
    int getNumber();
    void setNumber(int number);
}
