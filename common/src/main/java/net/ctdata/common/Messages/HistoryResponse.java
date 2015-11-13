package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.SensorMessage;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class HistoryResponse extends SensorMessage {

    List<Observation> observations = new LinkedList<Observation>();
    UUID requestId;

    /**
     * The ID of the HistoryRequest for which this response contains the data
     * @return {UUID}
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * {@link HistoryResponse#getRequestId()}
     */
    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

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

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("datapoints.history.%s", getRequestId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HistoryResponse that = (HistoryResponse) o;

        if (observations != null ? !observations.equals(that.observations) : that.observations != null) return false;
        return !(requestId != null ? !requestId.equals(that.requestId) : that.requestId != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (observations != null ? observations.hashCode() : 0);
        result = 31 * result + (requestId != null ? requestId.hashCode() : 0);
        return result;
    }
}
