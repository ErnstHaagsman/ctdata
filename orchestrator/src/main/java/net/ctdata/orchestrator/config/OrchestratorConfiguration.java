package net.ctdata.orchestrator.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class OrchestratorConfiguration {
    private final String rabbitMqUrl;

    @JsonCreator
    public OrchestratorConfiguration(@JsonProperty("rabbitMqUrl") String rabbitMqUrl){
        this.rabbitMqUrl = rabbitMqUrl;
    }

    public String getRabbitMqUrl() {
        return rabbitMqUrl;
    }
}
