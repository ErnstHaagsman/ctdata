package net.ctdata.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Messages.Connect;
import net.ctdata.common.Options.CliOptions;
import net.ctdata.common.Options.DefaultConfig;
import net.ctdata.common.Queue.Listeners.AddNodeListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;


public class Orchestrator {

    public static void main(String[] args) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
        Options options = CliOptions.getOptions();
        DefaultConfig configuration = CliOptions.readOptions(args, options, "java -jar Orchestrator.jar", DefaultConfig.class);

        // Just to make the compiler shut up
        if(configuration == null) return;

        System.out.println("Orchestration server started successfully!!");
        System.out.println("Setting up the RabbitMQ connection..");
        final RabbitMqConnection conn = new RabbitMqConnection(configuration.getRabbitMqUrl());
        System.out.println("RabbitMQ connection established..");

        conn.RegisterListener(new AddNodeListener() {
            @Override
            public void HandleMessage(AddNode message) {
                System.out.println("ADD_NODE: Received message from user "+ message.getUserId()
                    + " for adding the raspberry node having URL " + message.getNodeURL());
                Connect connectMessage = new Connect();
                connectMessage.setGatewayId(UUID.randomUUID());
                connectMessage.setNodeURL(message.getNodeURL());
                conn.SendMessage(connectMessage);
                System.out.println("CONNECT: Sent message for node URL "+ message.getNodeURL()
                    + " and gateway Id "+ connectMessage.getGatewayId());
            }
        });
    }
}
