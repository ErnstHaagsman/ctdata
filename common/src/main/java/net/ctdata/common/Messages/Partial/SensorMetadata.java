package net.ctdata.common.Messages.Partial;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class SensorMetadata {
    public int sensor;
    public String name;
    public int pollingInterval;
    public String type;
    public double latitude;
    public double longitude;

    /**
     * The number of the sensor on the Raspberry Pi
     * @return {int}
     */
    public int getSensor() {
        return sensor;
    }

    /**
     * @see {@link SensorMetadata#getSensor()}
     */
    public void setSensor(int sensor) {
        this.sensor = sensor;
    }

    /**
     * Human-readable name for the sensor
     * @return {String}
     */
    public String getName() {
        return name;
    }

    /**
     * @see {@link SensorMetadata#getName()}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Polling interval of the sensor, in milliseconds
     * @return {int}
     */
    public int getPollingInterval() {
        return pollingInterval;
    }

    /**
     * @see {@link SensorMetadata#getPollingInterval()}
     */
    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    /**
     * The type of sensor, for example "Temperature", or "Humidity"
     * @return {String}
     */
    public String getType() {
        return type;
    }

    /**
     * @see {@link SensorMetadata#getType()}
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * WGS84 Decimal latitude of the sensor
     * @return {double}
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @see {@link SensorMetadata#getLatitude()}
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * WGS84 Decimal longitude of the sensor
     * @return {double}
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @see {@link SensorMetadata#getLongitude()}
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorMetadata that = (SensorMetadata) o;

        if (sensor != that.sensor) return false;
        if (pollingInterval != that.pollingInterval) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = sensor;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + pollingInterval;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
