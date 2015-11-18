package net.ctdata.raspnodesim;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.ctdata.raspnodesim.config.NodeConfiguration;
import net.ctdata.raspnodesim.datacollection.CollectionThread;
import net.ctdata.raspnodesim.router.ConsoleListener;
import net.ctdata.raspnodesim.router.DataRouter;
import net.ctdata.raspnodesim.sensors.RandomZeroHundredSensor;
import net.ctdata.raspnodesim.sensors.Sensor;
import net.ctdata.raspnodesim.websocket.RaspNodeServer;
import org.joda.time.Period;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class RaspNodeSim {

    public static void main(String[] args) throws IOException {
        NodeConfiguration configuration = new NodeConfiguration(UUID.randomUUID());
        DataRouter router = new DataRouter();

        // S1 - location: SJSU Library
        Sensor s1 = new RandomZeroHundredSensor(5000, 1);
        s1.setLatitude(37.335571);
        s1.setLongitude(-121.884661);
        configuration.getConnectedSensors().add(s1);

        // S2 - location: BBC
        Sensor s2 = new RandomZeroHundredSensor(3000, 2);
        s2.setLatitude(37.337079);
        s2.setLongitude(-121.878867);
        configuration.getConnectedSensors().add(s2);

        String configJson = configuration.toJSON();
        System.out.println(configJson);
        configuration = NodeConfiguration.fromJSON(configJson);

        RaspNodeServer websocketServer = new RaspNodeServer();
        websocketServer.start();

        router.AddListener(new ConsoleListener());
        router.AddListener(websocketServer);

        Thread collectionThread = new Thread(new CollectionThread(configuration, router));
        collectionThread.start();

        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
