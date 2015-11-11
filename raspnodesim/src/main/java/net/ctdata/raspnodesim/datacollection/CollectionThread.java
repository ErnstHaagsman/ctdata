package net.ctdata.raspnodesim.datacollection;

import net.ctdata.raspnodesim.sensors.Sensor;
import org.joda.time.DateTime;

import java.util.List;

public class CollectionThread implements Runnable {

    List<Sensor> connectedSensors;

    public CollectionThread(List<Sensor> connectedSensors){
        this.connectedSensors = connectedSensors;
    }

    @Override
    public void run() {
        while(true){
            DateTime now = DateTime.now();
            for(Sensor sensor : connectedSensors){
                if (sensor.getNextObservationTime().isBefore(now)){
                    System.out.println(String.format("Sensor %d observed data: %f", sensor.getNumber(), sensor.getData()));
                    sensor.advanceObservationTime();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
