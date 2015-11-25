package net.ctdata.sensorgateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Messages.Connect;
import net.ctdata.common.Messages.RequestNodes;
import net.ctdata.common.Queue.Listeners.ConnectListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.sensorgateway.config.CliOptions;
import net.ctdata.sensorgateway.config.GatewayConfiguration;
import net.ctdata.sensorgateway.websocket.RaspNodeClient;
import org.apache.commons.cli.*;

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
        CommandLineParser parser = new DefaultParser();
        Options options = CliOptions.getOptions();

        GatewayConfiguration configuration = null;

        try {
            CommandLine cmd = parser.parse(options, args);

            String fileName = cmd.getOptionValue(CliOptions.OPTIONS_CONFIG);
            File configFile = new File(fileName);

            ObjectMapper mapper = new MapperSingleton().getMapper();
            configuration = mapper.readValue(configFile, GatewayConfiguration.class);

        } catch (ParseException e) {
            System.err.println("Could not parse CLI options: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("raspnode", options);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Could not read configuration file: " + e.getMessage());
            System.exit(-1);
        }


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
                client.connect();
                connectedNodes.add(client);
            }
        });
    }
}
