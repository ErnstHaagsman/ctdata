package net.ctdata.raspnodesim.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.raspnodesim.sensors.Sensor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class NodeConfiguration {
    private UUID nodeUUID;
    private List<Sensor> connectedSensors;

    @JsonCreator
    public NodeConfiguration(@JsonProperty("nodeUUID") UUID nodeUUID){
        this.nodeUUID = nodeUUID;
        this.connectedSensors = new LinkedList<Sensor>();
    }

    public UUID getNodeUUID() {
        return nodeUUID;
    }

    public List<Sensor> getConnectedSensors() {
        return connectedSensors;
    }

    public String toJSON() throws JsonProcessingException {
        return new MapperSingleton().getMapper().writeValueAsString(this);
    }

    public static NodeConfiguration fromJSON(String json) throws IOException {
        ObjectMapper mapper = new MapperSingleton().getMapper();
        return mapper.readValue(json, NodeConfiguration.class);
    }
}
