package net.ctdata.common.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandlerImpl;
import net.ctdata.common.Queue.Message;

public abstract class AbstractMessage implements Message {

    @Override
    @JsonIgnore
    public String getRoutingKey() {
        return "";
    }

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
