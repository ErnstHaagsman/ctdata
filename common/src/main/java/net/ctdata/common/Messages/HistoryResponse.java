package net.ctdata.common.Messages;

import java.util.List;
import java.util.UUID;

public class HistoryResponse extends RaspberryMessage {

    List<Observation> observations;

    /**
     * The observations stored for the requested time period
     *
     * @see {@link HistoryRequest}
     * @see {@link Observation}
     * @return {List<Observation>}
     */
    public List<Observation> getObservations() {
        return observations;
    }

    /**
     * @see {@link HistoryResponse#getObservations()}
     */
    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HistoryResponse that = (HistoryResponse) o;

        return !(observations != null ? !observations.equals(that.observations) : that.observations != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (observations != null ? observations.hashCode() : 0);
        return result;
    }
}
