package net.ctdata.common.Messages;

import org.joda.time.DateTime;

import java.util.UUID;

public class IncomingObservationMessage {

    private UUID raspberryNode;
    private int sensor;
    private DateTime time;
    private double observation;
    private double latitude;
    private double longitude;

    /**
     * The unique ID for the raspberry node which reported this observation
     *
     * @return {UUID}
     */
    public UUID getRaspberryNode() {
        return raspberryNode;
    }

    /**
     * @see {@link IncomingObservationMessage#getRaspberryNode()}
     */
    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }

    /**
     * The sensor on the raspberry node which made this observation. The sensor is only unique within
     * the raspberry node. For unique identification of a sensor within the network, both the Raspberry Node's ID
     * and the sensor number are necessary.
     *
     * @return {int}
     */
    public int getSensor() {
        return sensor;
    }

    /**
     * @see {@link IncomingObservationMessage#getSensor()}
     */
    public void setSensor(int sensor) {
        this.sensor = sensor;
    }

    /**
     * The time at which this observation was made. Please ensure the timezone information is correct.
     *
     * @return {org.joda.time.DateTime}
     */
    public DateTime getTime() {
        return time;
    }

    /**
     * @see {@link IncomingObservationMessage#getTime()}
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
     * @see {@link IncomingObservationMessage#getObservation()}
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
     * @see {@link IncomingObservationMessage#getLatitude()}
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
     * @see {@link IncomingObservationMessage#getLongitude()}}
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
