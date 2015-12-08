package net.ctdata.raspnodesim;

import net.ctdata.common.Options.CliOptions;
import net.ctdata.raspnodesim.config.NodeConfiguration;
import net.ctdata.raspnodesim.datacollection.CollectionThread;
import net.ctdata.raspnodesim.observationcache.CacheListener;
import net.ctdata.raspnodesim.observationcache.MemoryCache;
import net.ctdata.raspnodesim.observationcache.ObservationCache;
import net.ctdata.raspnodesim.router.ConsoleListener;
import net.ctdata.raspnodesim.router.DataRouter;
import net.ctdata.raspnodesim.websocket.RaspNodeServer;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RaspNodeSim {

    public static void main(String[] args){

        CommandLineParser parser = new DefaultParser();
        Options options = CliOptions.getOptions();

        NodeConfiguration configuration = null;

        try {
            CommandLine cmd = parser.parse(options, args);

            String fileName = cmd.getOptionValue(CliOptions.OPTIONS_CONFIG);
            File configFile = new File(fileName);
            configuration = NodeConfiguration.fromFile(configFile);

        } catch (ParseException e) {
            System.err.println("Could not parse CLI options: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("raspnode", options);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Could not read configuration file: " + e.getMessage());
            System.exit(-1);
        }

        DataRouter router = new DataRouter();

        ObservationCache cache = new MemoryCache();

        RaspNodeServer websocketServer = new RaspNodeServer(cache, configuration);
        websocketServer.start();

        router.AddListener(new ConsoleListener());
        router.AddListener(websocketServer);
        router.AddListener(new CacheListener(cache));

        Thread collectionThread = new Thread(new CollectionThread(configuration, router));
        collectionThread.start();

        Scanner s = new Scanner(System.in);
        s.nextLine();
    }
}
