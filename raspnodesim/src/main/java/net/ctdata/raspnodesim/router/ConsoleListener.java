package net.ctdata.raspnodesim.router;

import net.ctdata.common.Messages.Observation;

public class ConsoleListener implements DataListener {
    @Override
    public void NewObservation(Observation observation) {
        System.out.println(String.format("Sensor %d observed data: %f", observation.getSensor(), observation.getObservation()));
    }
}
