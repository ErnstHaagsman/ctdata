package net.ctdata.common.Options;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

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

    /**
     * Read command line arguments
     * @param args The arguments array in the main method
     * @param options An options objects (use CliOptions.getOptions, extend if you wish)
     * @param usage The name of your app (it's used in the help text, i.e. "java -jar SensorGateway.jar"
     * @param returnType Your configuration object's class (i.e. DefaultConfig.class)
     * @param <T> Same as returntype
     * @return The JSON configuration file deserialized
     */
    public static <T> T readOptions(String[] args, Options options, String usage, Class returnType) {
        return readOptions(args, options,usage,returnType,false);
    }

    /**
     * Read command line arguments
     * @param args The arguments array in the main method
     * @param options An options objects (use CliOptions.getOptions, extend if you wish)
     * @param usage The name of your app (it's used in the help text, i.e. "java -jar SensorGateway.jar"
     * @param returnType Your configuration object's class (i.e. DefaultConfig.class)
     * @param <T> Same as returntype
     * @return The JSON configuration file deserialized
     */
    @SuppressWarnings("unchecked")
    public static <T> T readOptions(String[] args, Options options, String usage, Class returnType, boolean ignoreUnknownOptions){
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args, ignoreUnknownOptions);

            String fileName = cmd.getOptionValue(CliOptions.OPTIONS_CONFIG);
            File configFile = new File(fileName);

            ObjectMapper mapper = new MapperSingleton().getMapper();
            return (T)mapper.readValue(configFile, returnType);

        } catch (ParseException e) {
            System.err.println("Could not parse CLI options: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage, options);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Could not read configuration file: " + e.getMessage());
            System.exit(-1);
        }

        // This will never happen, the application will have exited if it didn't return before
        return null;
    }
}
