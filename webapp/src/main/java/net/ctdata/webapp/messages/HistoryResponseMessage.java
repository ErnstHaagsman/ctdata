package net.ctdata.webapp.messages;

import net.ctdata.common.Messages.HistoryResponse;
import net.ctdata.common.Messages.Observation;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class HistoryResponseMessage {
    private List<ObservationMessage> observations = new LinkedList<ObservationMessage>();
    private UUID raspberryNode;
    private int sensorNumber;

    public HistoryResponseMessage(HistoryResponse response){
        this.raspberryNode = response.getRaspberryNode();
        this.sensorNumber = response.getSensor();
        for (Observation observation : response.getObservations())
            observations.add(new ObservationMessage(observation));
    }

    public String getMessageType(){
        return HistoryResponseMessage.class.getName();
    }

    public List<ObservationMessage> getObservations() {
        return observations;
    }

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
