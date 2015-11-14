package net.ctdata.raspnodesim.router;

import net.ctdata.common.Messages.Observation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class RouteThread implements Runnable {
    BlockingQueue<Observation> observations;
    DataListener listener;

    public RouteThread(DataListener listener){
        observations = new LinkedBlockingQueue<Observation>();
        this.listener = listener;
    }

    public void AddObservation(Observation observation) throws InterruptedException {
        observations.put(observation);
    }

    @Override
    public void run() {
        while(true){
            try {
                Observation observation = observations.take();
                listener.NewObservation(observation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
