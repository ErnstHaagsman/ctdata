package net.ctdata.raspnodesim.datacollection;

import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodesim.router.DataRouter;
import net.ctdata.raspnodesim.sensors.Sensor;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public class CollectionThread implements Runnable {

    UUID raspnodeId;
    List<Sensor> connectedSensors;
    DataRouter router;

    public CollectionThread(UUID raspnodeId, List<Sensor> connectedSensors, DataRouter router){
        this.raspnodeId = raspnodeId;
        this.connectedSensors = connectedSensors;
        this.router = router;
    }

    @Override
    public void run() {
        while(true){
            DateTime now = DateTime.now();
            for(Sensor sensor : connectedSensors){
                if (sensor.getNextObservationTime().isBefore(now)){
                    router.AddObservation(sensor.getObservation(raspnodeId));
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
