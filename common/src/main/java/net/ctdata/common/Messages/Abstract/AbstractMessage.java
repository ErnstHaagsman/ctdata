package net.ctdata.common.Messages.Abstract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Queue.Message;
import net.ctdata.common.Queue.QueueListener;

import java.util.TimeZone;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "messageType")
public abstract class AbstractMessage implements Message {

    /**
     * Always override this method for messages which go onto the messaging queue.
     *
     * Make sure to always use the @JsonIgnore annotation to prevent it from being included in the actual message body
     *
     * @see {@link QueueListener#getRoutingKeyPattern()}
     * @return {String}
     */
    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return "";
    }

    /**
     * Serializes the message into JSON
     *
     * @return {String} JSON string
     */
    @Override
    @JsonIgnore
    public String getBody() {
        ObjectMapper mapper = new MapperSingleton().getMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {}

        return "";
    }

}
