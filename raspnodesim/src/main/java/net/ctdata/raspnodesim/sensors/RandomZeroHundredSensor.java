package net.ctdata.raspnodesim.sensors;

import org.joda.time.Period;

public class RandomZeroHundredSensor extends AbstractSensor {

    public RandomZeroHundredSensor(){
        super();
    }

    public RandomZeroHundredSensor(int pollingInterval, int number){
        super(pollingInterval, number);
    }

    @Override
    public double getData() {
        return Math.random() * 100;
    }
}
