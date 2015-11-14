package net.ctdata.raspnodesim;

import net.ctdata.raspnodesim.datacollection.CollectionThread;
import net.ctdata.raspnodesim.router.ConsoleListener;
import net.ctdata.raspnodesim.router.DataRouter;
import net.ctdata.raspnodesim.sensors.RandomZeroHundredSensor;
import net.ctdata.raspnodesim.sensors.Sensor;
import org.joda.time.Period;

import java.util.*;

public class RaspNodeSim {

    public static void main(String[] args) {
        List<Sensor> connectedSensors = new LinkedList<Sensor>();
        UUID raspNodeId = UUID.randomUUID();
        DataRouter router = new DataRouter();

        connectedSensors.add(new RandomZeroHundredSensor(new Period(0,0,5,0), 1));
        connectedSensors.add(new RandomZeroHundredSensor(new Period(0,0,3,0), 2));

        router.AddListener(new ConsoleListener());
        router.AddListener(new ConsoleListener());

        Thread collectionThread = new Thread(new CollectionThread(raspNodeId, connectedSensors, router));
        collectionThread.start();

        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
