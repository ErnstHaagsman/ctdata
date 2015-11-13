package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Queue.Message;
import org.joda.time.Interval;

import java.util.UUID;

public class HistoryRequest extends SensorMessage implements Message {

    private UUID requestID;
    private Interval timePeriod;

    /**
     * The time interval for which to get this sensor's data
     * @return {org.joda.time.Interval}
     */
    public Interval getTimePeriod() {
        return timePeriod;
    }

    /**
     * @see {@link HistoryRequest#getTimePeriod()}
     */
    public void setTimePeriod(Interval timePeriod) {
        this.timePeriod = timePeriod;
    }

    /**
     * A randomly generated ID for this request, the return message will use this ID in its topic
     * @return {UUID}
     */
    public UUID getRequestID() {
        return requestID;
    }

    public HistoryRequest(){
        this.requestID = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        HistoryRequest that = (HistoryRequest) o;

        if (requestID != null ? !requestID.equals(that.requestID) : that.requestID != null) return false;
        return !(timePeriod != null ? !timePeriod.equals(that.timePeriod) : that.timePeriod != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (requestID != null ? requestID.hashCode() : 0);
        result = 31 * result + (timePeriod != null ? timePeriod.hashCode() : 0);
        return result;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("datapoints.request.%s.%d", this.getRaspberryNode(), this.getSensor());
    }
}
