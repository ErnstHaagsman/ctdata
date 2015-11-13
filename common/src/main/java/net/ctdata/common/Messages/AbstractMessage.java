package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandlerImpl;
import net.ctdata.common.Queue.Message;
import net.ctdata.common.Queue.QueueListener;

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
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {}

        return "";
    }

}
