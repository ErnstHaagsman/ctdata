package net.ctdata.webapp.messages;

import java.util.UUID;

public class HistoryRequestMessage {
    private UUID raspberryNode;
    private int sensorNumber;

    public UUID getRaspberryNode() {
        return raspberryNode;
    }

    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }

    public int getSensorNumber() {
        return sensorNumber;
    }

    public void setSensorNumber(int sensorNumber) {
        this.sensorNumber = sensorNumber;
    }
}
