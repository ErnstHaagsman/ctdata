package net.ctdata.raspnodesim.router;

import net.ctdata.common.Messages.Observation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class RouteThread implements Runnable {
    BlockingQueue<Observation> observations;
    DataListener listener;
    boolean running;

    public RouteThread(DataListener listener){
        observations = new LinkedBlockingQueue<Observation>();
        this.listener = listener;
    }

    public void AddObservation(Observation observation) throws InterruptedException {
        observations.put(observation);
    }

    public void start(){
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop(){
        running = false;
    }

    @Override
    public void run() {
        while(running){
            try {
                Observation observation = observations.take();
                if(running) listener.NewObservation(observation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
