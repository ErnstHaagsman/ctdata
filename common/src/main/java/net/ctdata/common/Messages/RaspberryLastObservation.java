package net.ctdata.common.Messages;

import net.ctdata.common.Messages.Abstract.RaspberryMessage;
import net.ctdata.common.Messages.Partial.SensorLastObservation;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by aditi on 28/11/15.
 */
public class RaspberryLastObservation extends RaspberryMessage {

    List<SensorLastObservation> sensors = new LinkedList<SensorLastObservation>();
    private String nodeURL;

    /**
     * Last observation data of each sensor of the node
     * @return {List}
     */
    public List<SensorLastObservation> getSensors() {
        return sensors;
    }


    /**
     * @see {@link RaspberryLastObservation#sensors}
     */
    public void setSensors(List<SensorLastObservation> sensors) {
        this.sensors = sensors;
    }

    /**
     * The node URL
     * @return {String}
     */
    public String getNodeURL() {
        return nodeURL;
    }


    /**
     * @see {@link RaspberryLastObservation#nodeURL}
     */
    public void setNodeURL(String nodeURL) {
        this.nodeURL = nodeURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RaspberryLastObservation that = (RaspberryLastObservation) o;
        return Objects.equals(sensors, that.sensors) &&
                Objects.equals(nodeURL, that.nodeURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sensors, nodeURL);
    }
}
