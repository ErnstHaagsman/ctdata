package net.ctdata.sensorgateway.config;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CliOptions {
    public static String OPTIONS_CONFIG = "config";

    public static Options getOptions(){
        Options options = new Options();

        Option configFile = new Option(OPTIONS_CONFIG, "Path to JSON configuration file");
        configFile.setRequired(true);
        configFile.setArgs(1);
        configFile.setArgName("json-file");
        options.addOption(configFile);

        return options;
    }
}
