package net.ctdata.webapp.controllers;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Nimesh on 11/28/2015.
 */
public class AddedNode {
    public String url;
    public UUID nodeID;
    public double sId;
    public String sensorName;
    public String sensorType;
    public int pollingInterval;
    public double latitude;
    public double longitude;
    public double lastObservation;
    public LinkedList<AddedNode> addedNodes = new LinkedList<AddedNode>();

    public LinkedList<AddedNode> getAddedNodes() {
        return addedNodes;
    }

    public void setAddedNodes(LinkedList<AddedNode> addedNodes) {
        this.addedNodes = addedNodes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
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
