package net.ctdata.raspnodesim.router;

import net.ctdata.common.Messages.Observation;
import net.ctdata.raspnodesim.router.DataListener;

import java.util.LinkedList;
import java.util.List;

public class DataRouter {
    List<RouteThread> threads = new LinkedList<RouteThread>();

    public void AddObservation(Observation observation){
        for (RouteThread thread : threads){
            try {
                thread.AddObservation(observation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void AddListener(DataListener listener){
        RouteThread routeThread = new RouteThread(listener);
        Thread thread = new Thread(routeThread);
        thread.start();
        threads.add(routeThread);
    }
}
