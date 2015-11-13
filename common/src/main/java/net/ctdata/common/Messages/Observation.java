package net.ctdata.common.Messages;

import org.joda.time.DateTime;

import java.util.UUID;

public class Observation extends SensorMessage {

    private DateTime time;
    private double observation;
    private double latitude;
    private double longitude;

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

}
