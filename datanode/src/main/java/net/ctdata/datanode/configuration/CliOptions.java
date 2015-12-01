package net.ctdata.datanode.configuration;

/**
 * Created by aditi on 30/11/15.
 */
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CliOptions {
    public static String OPTIONS_DBCONFIG = "dbconfig";

    public static String OPTIONS_QCONFIG = "queueconfig";

    public static Options getOptions(){
        Options options = new Options();

        Option dbconfigFile = new Option(OPTIONS_DBCONFIG, "Path to JSON configuration file");
        dbconfigFile.setRequired(true);
        dbconfigFile.setArgs(1);
        dbconfigFile.setArgName("db-json-file");
        options.addOption(dbconfigFile);

        Option qconfigFile = new Option(OPTIONS_QCONFIG, "Path to JSON configuration file");
        qconfigFile.setRequired(true);
        qconfigFile.setArgs(1);
        qconfigFile.setArgName("queue-json-file");
        options.addOption(qconfigFile);

        return options;
    }
}
