package net.ctdata.datanode.configuration;

/**
 * Created by aditi on 30/11/15.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseConfiguration {
    private final String databaseHost;

    private final String databasePort;

    private final String databaseUser;

    private final String databasePswd;

    private final String databaseSchema;

    @JsonCreator
    public DatabaseConfiguration(@JsonProperty("databasehost") String databaseHost,
                                 @JsonProperty("databaseport") String databasePort,
                                 @JsonProperty("databaseuser") String databaseUser,
                                 @JsonProperty("databasepswd") String databasePswd,
                                 @JsonProperty("databaseschema") String databaseSchema) {
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseUser = databaseUser;
        this.databasePswd = databasePswd;
        this.databaseSchema = databaseSchema;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePswd() {
        return databasePswd;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }
}
