package net.ctdata.datanode.dataresources;

import java.util.UUID;

/**
 * Created by aditi on 14/11/15.
 * This class is a Java Object of the database table Observations
 */
public class Observations {
    private UUID raspberryNode;
    private int sensorId;
    private Double observationData;
    private String observationTime;
    private char acknowledgementFlag;
    private Double latitude;
    private Double longitude;

    public Observations() {
    }

    public Observations(UUID raspberryNode, int sensorId, Double observationData, String observationTime, char acknowledgementFlag, Double latitude, Double longitude) {
        this.raspberryNode = raspberryNode;
        this.sensorId = sensorId;
        this.observationData = observationData;
        this.observationTime = observationTime;
        this.acknowledgementFlag = acknowledgementFlag;
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

    public Double getObservationData() {
        return observationData;
    }

    public void setObservationData(Double observationData) {
        this.observationData = observationData;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public char getAcknowledgementFlag() {
        return acknowledgementFlag;
    }

    public void setAcknowledgementFlag(char acknowledgementFlag) {
        this.acknowledgementFlag = acknowledgementFlag;
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
