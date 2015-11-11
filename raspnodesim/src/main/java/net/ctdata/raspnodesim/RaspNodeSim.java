package net.ctdata.raspnodesim;

import net.ctdata.raspnodesim.datacollection.CollectionThread;
import net.ctdata.raspnodesim.sensors.RandomZeroHundredSensor;
import net.ctdata.raspnodesim.sensors.Sensor;
import org.joda.time.Period;

import java.util.*;

public class RaspNodeSim {

    public static void main(String[] args) {
        List<Sensor> connectedSensors = new LinkedList<Sensor>();

        connectedSensors.add(new RandomZeroHundredSensor(new Period(0,0,5,0), 1));

        Thread collectionThread = new Thread(new CollectionThread(connectedSensors));
        collectionThread.start();

        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
