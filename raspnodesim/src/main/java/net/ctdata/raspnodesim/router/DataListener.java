package net.ctdata.raspnodesim.router;

import net.ctdata.common.Messages.Observation;

public interface DataListener {
    void NewObservation(Observation observation);
}
