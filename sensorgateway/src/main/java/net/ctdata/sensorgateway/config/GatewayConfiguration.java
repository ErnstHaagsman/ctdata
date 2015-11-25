package net.ctdata.sensorgateway.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GatewayConfiguration {
    private final String rabbitMqUrl;

    @JsonCreator
    public GatewayConfiguration(@JsonProperty("rabbitMqUrl") String rabbitMqUrl){
        this.rabbitMqUrl = rabbitMqUrl;
    }

    public String getRabbitMqUrl() {
        return rabbitMqUrl;
    }
}
