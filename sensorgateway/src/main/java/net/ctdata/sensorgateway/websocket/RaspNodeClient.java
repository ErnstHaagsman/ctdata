package net.ctdata.sensorgateway.websocket;

import net.ctdata.common.Queue.RabbitMqConnection;

import java.net.URI;

public class RaspNodeClient extends Thread {
    private RaspNodeWebsocketClient client;
    final URI nodeURL;
    final RabbitMqConnection conn;

    public RaspNodeClient(URI nodeURL, RabbitMqConnection conn){
        this.nodeURL = nodeURL;
        this.conn = conn;
        this.start();
    }

    @Override
    public void run() {
        startNewClient();
    }

    private void startNewClient(){
        client = new RaspNodeWebsocketClient(nodeURL, conn, this);
        client.connect();
    }

    void onClose(){
        try {
            Thread.sleep(2000);
            startNewClient();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
