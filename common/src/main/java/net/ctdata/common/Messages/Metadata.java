package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.RaspberryMessage;
import net.ctdata.common.Messages.Partial.SensorMetadata;

import java.util.LinkedList;
import java.util.List;

public class Metadata extends RaspberryMessage {
    private String nodeUrl;
    List<SensorMetadata> sensors = new LinkedList<SensorMetadata>();

    /**
     * The metadata of all sensors connected to this Raspberry Pi node
     * @return {List<SensorMetadata>}
     */
    public List<SensorMetadata> getSensors() {
        return sensors;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public void setSensors(List<SensorMetadata> sensors) {
        this.sensors = sensors;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("notedata.meta.%s", getNodeUrl());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Metadata metadata = (Metadata) o;

        return !(sensors != null ? !sensors.equals(metadata.sensors) : metadata.sensors != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sensors != null ? sensors.hashCode() : 0);
        return result;
    }
}
