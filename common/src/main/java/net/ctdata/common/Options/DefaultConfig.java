package net.ctdata.common.Options;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultConfig {
    private final String rabbitMqUrl;

    @JsonCreator
    public DefaultConfig(@JsonProperty("rabbitMqUrl") String rabbitMqUrl){
        this.rabbitMqUrl = rabbitMqUrl;
    }

    public String getRabbitMqUrl() {
        return rabbitMqUrl;
    }
}
