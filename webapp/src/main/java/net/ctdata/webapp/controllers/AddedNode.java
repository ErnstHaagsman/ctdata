package net.ctdata.webapp.controllers;

import java.util.UUID;

/**
 * Created by Nimesh on 11/28/2015.
 */
public class AddedNode {
    private String url;
    private UUID nodeID;
    private double sId;
    private String sensorName;
    private String sensorType;
    private int pollingInterval;
    private double latitude;
    private double longitude;
    double lastObservation;

    public String getUrl() {
        return url;
    }

    public void setUrl(String nodeURL) {
        this.url = url;
    }

    public UUID getNodeID() {return nodeID;}

    public void setNodeID(UUID nodeID) {
        this.nodeID = nodeID;
    }

    public double getSId() {
        return sId;
    }

    public void setSId(double sId) {
        this.sId = sId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {this.sensorType = sensorType;}

    public int getPollingInterval() { return pollingInterval; }

    public void setPollingInterval(int pollingInterval) {this.pollingInterval = pollingInterval;}

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLastObservation() {
        return lastObservation;
    }

    public void setLastObservation(double lastObservation) {
        this.lastObservation = lastObservation;
    }
}
