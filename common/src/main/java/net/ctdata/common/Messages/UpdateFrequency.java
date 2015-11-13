package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.ctdata.common.Messages.Abstract.SensorMessage;

public class UpdateFrequency extends SensorMessage {
    private int pollingFrequency;

    /**
     * The desired interval between observations (in milliseconds) for the specified sensor
     * @return {int}
     */
    public int getPollingFrequency() {
        return pollingFrequency;
    }

    /**
     * @see {@link UpdateFrequency#getPollingFrequency()}
     */
    public void setPollingFrequency(int pollingFrequency) {
        this.pollingFrequency = pollingFrequency;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("nodedata.updatefrequency.%s", getRaspberryNode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UpdateFrequency that = (UpdateFrequency) o;

        return pollingFrequency == that.pollingFrequency;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + pollingFrequency;
        return result;
    }
}
