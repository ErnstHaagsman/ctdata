package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.UUID;

public class Observation extends SensorMessage {

    private DateTime time;
    private double observation;
    private double latitude;
    private double longitude;

    /**
     * The time at which this observation was made. The timezone in this field is UTC
     *
     *
     * @return {org.joda.time.DateTime}
     */
    public DateTime getTime() {
        return time;
    }

    /**
     * To set the time, use the following code:
     * <code>
     * DateTime dateTime = new LocalDateTime(timestamp.getTime()).toDateTime(DateTimeZone.UTC);
     * </code>
     *
     * @see {@link Observation#getTime()}
     */
    public void setTime(DateTime time) {
        this.time = time.toDateTime(DateTimeZone.UTC);
    }

    /**
     * The actual observation (e.g. the temperature, humidity) recorded
     *
     * @return {double}
     */
    public double getObservation() {
        return observation;
    }

    /**
     * @see {@link Observation#getObservation()}
     */
    public void setObservation(double observation) {
        this.observation = observation;
    }

    /**
     * The latitude at which this datapoint was observed, in decimal form.
     * <p>
     * For example, a datapoint collected at the San Jose airport would have its location set like:
     * <code>
     * observation.setLatitude(37.3626667);
     * observation.setLongitude(-121.9291111);
     * </code>
     *
     * @return {double}
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @see {@link Observation#getLatitude()}
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * The longitude at which this datapoint was observed, in decimal form.
     * <p>
     * For example, a datapoint collected at the San Jose airport would have its location set like:
     * <code>
     * observation.setLatitude(37.3626667);
     * observation.setLongitude(-121.9291111);
     * </code>
     *
     * @return {double}
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @see {@link Observation#getLongitude()}}
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return String.format("datapoints.incoming.%s.%d", getRaspberryNode(), getSensor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Observation that = (Observation) o;

        if (Double.compare(that.observation, observation) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        return !(time != null ? !time.equals(that.time) : that.time != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        temp = Double.doubleToLongBits(observation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
