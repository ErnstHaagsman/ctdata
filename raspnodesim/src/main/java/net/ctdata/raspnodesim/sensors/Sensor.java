package net.ctdata.raspnodesim.sensors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Messages.Partial.SensorMetadata;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "sensorType")
public interface Sensor {
    @JsonIgnore
    double getData();

    @JsonIgnore
    Observation getObservation(UUID raspNodeId);

    @JsonIgnore
    DateTime getNextObservationTime();

    @JsonIgnore
    SensorMetadata getMetadata();
    
    void advanceObservationTime();

    /**
     * In milliseconds
     * @return
     */
    int getPollingInterval();
    void setPollingInterval(int pollingInterval);
    int getNumber();
    void setNumber(int number);
    double getLatitude();
    void setLatitude(double latitude);
    double getLongitude();
    void setLongitude(double longitude);
    String getType();
    void setType(String type);
    String getName();
    void setName(String name);

    void registerChangeListener(SensorConfigurationChangedListener listener);
    void unregisterChangeListener(SensorConfigurationChangedListener listener);
}
