package net.ctdata.datanode.configuration;

/**
 * Created by aditi on 30/11/15.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RabbitMQConfiguration {
    private final String rabbitmqUri;

    @JsonCreator
    public RabbitMQConfiguration(@JsonProperty("rabbitmqUrl") String rabbitmqUri) {
        this.rabbitmqUri = rabbitmqUri;
    }

    public String getRabbitmqUri() {
        return rabbitmqUri;
    }
}
