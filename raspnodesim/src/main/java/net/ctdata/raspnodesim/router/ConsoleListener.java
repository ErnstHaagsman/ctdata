package net.ctdata.raspnodesim.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodeprotocol.WebsocketMessage;

public class ConsoleListener implements DataListener {
    @Override
    public void NewObservation(Observation observation) {
        System.out.println(String.format("Sensor %d observed data: %f", observation.getSensor(), observation.getObservation()));
    }
}
