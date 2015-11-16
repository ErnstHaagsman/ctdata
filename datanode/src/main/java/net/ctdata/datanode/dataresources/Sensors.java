package net.ctdata.datanode.dataresources;

import java.util.UUID;

/**
 * Created by aditi on 14/11/15.
 * This class is a Java Object of the database table Sensors
 */
public class Sensors {

    private UUID raspberryNode;
    private int sensorId;
    private String sensorName;
    private String type;
    private int pollingFrequency;
    private String lastObservationTime;
    private Double latitude;
    private Double longitude;

    public Sensors() {
    }

    public Sensors(UUID raspberryNode, int sensorId, String sensorName, String type, int pollingFrequency, String lastObservationTime, Double latitude, Double longitude) {
        this.raspberryNode = raspberryNode;
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.type = type;
        this.pollingFrequency = pollingFrequency;
        this.lastObservationTime = lastObservationTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getRaspberryNode() {
        return raspberryNode;
    }

    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPollingFrequency() {
        return pollingFrequency;
    }

    public void setPollingFrequency(int pollingFrequency) {
        this.pollingFrequency = pollingFrequency;
    }

    public String getLastObservationTime() {
        return lastObservationTime;
    }

    public void setLastObservationTime(String lastObservationTime) {
        this.lastObservationTime = lastObservationTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
