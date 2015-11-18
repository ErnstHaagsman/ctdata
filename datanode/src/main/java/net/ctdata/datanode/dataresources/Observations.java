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

    public Observations() {
    }

    public Observations(UUID raspberryNode, int sensorId, Double observationData, String observationTime, char acknowledgementFlag) {
        this.raspberryNode = raspberryNode;
        this.sensorId = sensorId;
        this.observationData = observationData;
        this.observationTime = observationTime;
        this.acknowledgementFlag = acknowledgementFlag;
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
}
