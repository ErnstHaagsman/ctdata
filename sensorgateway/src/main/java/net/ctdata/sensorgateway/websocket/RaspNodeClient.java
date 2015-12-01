package net.ctdata.sensorgateway.websocket;

import net.ctdata.common.Messages.Disconnect;
import net.ctdata.common.Queue.Listeners.DisconnectListener;
import net.ctdata.common.Queue.RabbitMqConnection;

import java.net.URI;
import java.util.UUID;

public class RaspNodeClient extends Thread {
    private RaspNodeWebsocketClient client;
    final URI nodeURL;
    final RabbitMqConnection conn;
    DisconnectListener disconnectListener;
    boolean disconnect;

    public RaspNodeClient(URI nodeURL, RabbitMqConnection conn){
        this.nodeURL = nodeURL;
        this.conn = conn;
        this.start();
    }

    void setUUID(UUID raspberryNodeUUID){
        if(disconnectListener == null){
            disconnectListener = new DisconnectListener(raspberryNodeUUID) {
                @Override
                public void HandleMessage(Disconnect message) {
                    disconnect = true;
                    client.close();
                }
            };
            conn.RegisterListener(disconnectListener);
        }
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
            if (!disconnect) {
                Thread.sleep(2000);
                startNewClient();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
