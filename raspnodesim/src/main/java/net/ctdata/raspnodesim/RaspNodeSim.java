package net.ctdata.raspnodesim;

import net.ctdata.raspnodesim.datacollection.CollectionThread;
import net.ctdata.raspnodesim.router.ConsoleListener;
import net.ctdata.raspnodesim.router.DataRouter;
import net.ctdata.raspnodesim.sensors.RandomZeroHundredSensor;
import net.ctdata.raspnodesim.sensors.Sensor;
import net.ctdata.raspnodesim.websocket.RaspNodeServer;
import org.joda.time.Period;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class RaspNodeSim {

    public static void main(String[] args) {
        List<Sensor> connectedSensors = new LinkedList<Sensor>();
        UUID raspNodeId = UUID.randomUUID();
        DataRouter router = new DataRouter();

        // S1 - location: SJSU Library
        Sensor s1 = new RandomZeroHundredSensor(new Period(0,0,5,0), 1);
        s1.setLatitude(37.335571);
        s1.setLongitude(-121.884661);
        connectedSensors.add(s1);

        // S2 - location: BBC
        Sensor s2 = new RandomZeroHundredSensor(new Period(0,0,3,0), 2);
        s2.setLatitude(37.337079);
        s2.setLongitude(-121.878867);
        connectedSensors.add(s2);

        RaspNodeServer websocketServer = new RaspNodeServer();
        websocketServer.start();

        router.AddListener(new ConsoleListener());
        router.AddListener(websocketServer);

        Thread collectionThread = new Thread(new CollectionThread(raspNodeId, connectedSensors, router));
        collectionThread.start();

        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
