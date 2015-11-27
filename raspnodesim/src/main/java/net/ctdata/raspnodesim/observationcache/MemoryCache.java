package net.ctdata.raspnodesim.observationcache;

import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodesim.router.DataListener;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.LinkedList;

public class MemoryCache implements ObservationCache {
    private HashMap<ObservationKey, Observation> observations = new HashMap<ObservationKey, Observation>();

    class ObservationKey {
        int sensor;
        DateTime time;

        ObservationKey(int sensor, DateTime time){
            this.sensor = sensor;
            this.time = time;
        }

        ObservationKey(Observation observation){
            this.sensor = observation.getSensor();
            this.time = observation.getTime();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ObservationKey that = (ObservationKey) o;

            if (sensor != that.sensor) return false;
            return !(time != null ? !time.equals(that.time) : that.time != null);

        }

        @Override
        public int hashCode() {
            int result = sensor;
            result = 31 * result + (time != null ? time.hashCode() : 0);
            return result;
        }
    }

    @Override
    public void AddObservation(Observation observation) {
        observations.put(new ObservationKey(observation), observation);
    }

    @Override
    public void GetObservations(DataListener listener) {
        LinkedList<Observation> observationList = new LinkedList<Observation>(observations.values());
        for (Observation observation : observationList)
            listener.NewObservation(observation);
    }

    @Override
    public void DeleteObservation(int sensor, DateTime time) {
        observations.remove(new ObservationKey(sensor,time));
    }
}
