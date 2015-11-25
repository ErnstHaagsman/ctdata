package net.ctdata.sensorgateway.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GatewayConfiguration {
    private final String rabbitMqUrl;

    private final UUID gatewayID = UUID.randomUUID();

    @JsonCreator
    public GatewayConfiguration(@JsonProperty("rabbitMqUrl") String rabbitMqUrl){
        this.rabbitMqUrl = rabbitMqUrl;
    }

    public String getRabbitMqUrl() {
        return rabbitMqUrl;
    }

    @JsonIgnore
    public UUID getGatewayID() {
        return gatewayID;
    }
}
