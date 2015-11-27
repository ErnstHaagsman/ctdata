package net.ctdata.raspnodesim.observationcache;

import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodesim.router.DataListener;
import org.joda.time.DateTime;

public interface ObservationCache {
    void AddObservation(Observation observation);
    void GetObservations(DataListener listener);
    void DeleteObservation(int sensor, DateTime time);
}
