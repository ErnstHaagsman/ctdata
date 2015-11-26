package net.ctdata.raspnodesim.observationcache;

import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodesim.router.DataListener;

public class CacheListener implements DataListener {
    final ObservationCache cache;

    public CacheListener(ObservationCache cache){
        this.cache = cache;
    }

    @Override
    public void NewObservation(Observation observation) {
        cache.AddObservation(observation);
    }
}
