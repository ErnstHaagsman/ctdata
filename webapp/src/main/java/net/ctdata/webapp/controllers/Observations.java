package net.ctdata.webapp.controllers;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by dhaval on 28-11-2015.
 */
public class Observations {

    private UUID raspberryNode;
    private int sensorId;
    private Double observationData;
    private Double latitude;
    private Double longitude;

    public Observations() {
    }

    public Observations(UUID raspberryNode, int sensorId, DateTime time, Double observationData, Double latitude, Double longitude) {
        this.raspberryNode = raspberryNode;
        this.sensorId = sensorId;
        this.observationData = observationData;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toString() {
        return this.raspberryNode + " : " + this.sensorId + " : " + this.observationData + " : " + this.latitude + " : " +this.longitude;
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
