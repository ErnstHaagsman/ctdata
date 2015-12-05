package net.ctdata.sensorgateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Messages.Connect;
import net.ctdata.common.Messages.RequestNodes;
import net.ctdata.common.Queue.Listeners.ConnectListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.common.Options.CliOptions;
import net.ctdata.sensorgateway.config.GatewayConfiguration;
import net.ctdata.sensorgateway.websocket.RaspNodeClient;
import org.apache.commons.cli.*;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class SensorGateway {
    public static void main(String[] args) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
        BasicConfigurator.configure();

        Options options = CliOptions.getOptions();
        GatewayConfiguration configuration = CliOptions.readOptions(args, options, "java -jar SensorGateway.jar", GatewayConfiguration.class);

        // To shut the compiler up
        if(configuration == null) return;


        final RabbitMqConnection conn = new RabbitMqConnection(configuration.getRabbitMqUrl());
        final List<RaspNodeClient> connectedNodes = new LinkedList<RaspNodeClient>();

        // Request the nodes this sensor gateway should connect to
        RequestNodes rn = new RequestNodes();
        rn.setGatewayId(configuration.getGatewayID());
        conn.SendMessage(rn);

        conn.RegisterListener(new ConnectListener() {
            @Override
            public void HandleMessage(Connect message) {
                RaspNodeClient client = new RaspNodeClient(URI.create(message.getNodeURL()), conn);
                connectedNodes.add(client);
            }
        });
    }
}
