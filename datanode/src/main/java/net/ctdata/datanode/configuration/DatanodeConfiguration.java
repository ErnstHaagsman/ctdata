package net.ctdata.datanode.configuration;

/**
 * Created by aditi on 30/11/15.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DatanodeConfiguration {
    private final String databaseHost;

    private final String databasePort;

    private final String databaseUser;

    private final String databasePswd;

    private final String databaseSchema;

    private final String rabbitMqUrl;

    @JsonCreator
    public DatanodeConfiguration(@JsonProperty("databasehost") String databaseHost,
                                 @JsonProperty("databaseport") String databasePort,
                                 @JsonProperty("databaseuser") String databaseUser,
                                 @JsonProperty("databasepassword") String databasePswd,
                                 @JsonProperty("rabbitMqUrl") String rabbitMqUrl,
                                 @JsonProperty("databaseschema") String databaseSchema) {
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.databaseUser = databaseUser;
        this.databasePswd = databasePswd;
        this.databaseSchema = databaseSchema;
        this.rabbitMqUrl = rabbitMqUrl;
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

    public String getRabbitMqUrl() {
        return rabbitMqUrl;
    }
}
