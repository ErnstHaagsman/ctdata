package net.ctdata.common.Messages.Abstract;

import net.ctdata.common.Messages.Observation;

public class SensorMessage extends RaspberryMessage {

    private int sensor;

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
     * @see {@link Observation#getSensor()}
     */
    public void setSensor(int sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SensorMessage that = (SensorMessage) o;

        return sensor == that.sensor;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + sensor;
        return result;
    }
}
