package net.ctdata.webapp.controllers;

import java.util.UUID;

/**
 * Created by Nimesh on 11/29/2015.
 */
public class Frequency {

    private UUID raspberryNode;
    private int sensor;
    private int frequencyN;


    public UUID getRaspberryNode() {
        return raspberryNode;
    }

    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }

    public int getSensor() {
        return sensor;
    }

    public void setSensor(int sensor) {
        this.sensor = sensor;
    }

    public int getFrequencyN() {
        return frequencyN;
    }

    public void setFrequency(int frequencyN) {
        this.frequencyN = frequencyN;
    }

}
