package net.ctdata.common.Messages;

import org.joda.time.DateTime;

public class Confirmation extends SensorMessage {

    private DateTime time;

    /**
     * The time at which this observation was made. Please ensure the timezone information is correct.
     *
     * @return {org.joda.time.DateTime}
     */
    public DateTime getTime() {
        return time;
    }

    /**
     * @see {@link Observation#getTime()}
     */
    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Confirmation that = (Confirmation) o;

        return !(time != null ? !time.equals(that.time) : that.time != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
