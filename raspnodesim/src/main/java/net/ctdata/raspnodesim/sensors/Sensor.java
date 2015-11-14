package net.ctdata.raspnodesim.sensors;

import net.ctdata.common.Messages.Observation;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

public interface Sensor {
    double getData();
    Observation getObservation(UUID raspNodeId);
    DateTime getNextObservationTime();
    void advanceObservationTime();
    Period getPollingInterval();
    void setPollingInterval(Period pollingInterval);
    int getNumber();
    void setNumber(int number);
    double getLatitude();
    void setLatitude(double latitude);
    double getLongitude();
    void setLongitude(double longitude);
}
