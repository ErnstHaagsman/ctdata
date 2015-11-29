package net.ctdata.common.Messages.Partial;

import java.util.Objects;

/**
 * Created by aditi on 28/11/15.
 */
public class SensorLastObservation extends SensorMetadata {

    private double lastObservation;

    /**
     * Last observation data of the sensor
     * @return {Double}
     */
    public double getLastObservation() {
        return lastObservation;
    }

    /**
     * @see {@link SensorLastObservation#lastObservation}
     */
    public void setLastObservation(double lastObservation) {
        this.lastObservation = lastObservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SensorLastObservation that = (SensorLastObservation) o;
        return Double.compare(that.lastObservation, lastObservation) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lastObservation);
    }
}
