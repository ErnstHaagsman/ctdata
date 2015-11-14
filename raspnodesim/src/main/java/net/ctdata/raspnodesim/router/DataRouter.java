package net.ctdata.raspnodesim.router;

import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodesim.router.DataListener;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class DataRouter {
    HashMap<DataListener, RouteThread> threads = new LinkedHashMap<DataListener, RouteThread>();

    public void AddObservation(Observation observation){
        for (RouteThread thread : threads.values()){
            try {
                thread.AddObservation(observation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void AddListener(DataListener listener){
        RouteThread routeThread = new RouteThread(listener);
        routeThread.start();
        threads.put(listener, routeThread);
    }

    public void RemoveListener(DataListener listener){
        RouteThread routeThread = threads.get(listener);
        routeThread.stop();
        threads.remove(listener);
    }
}
