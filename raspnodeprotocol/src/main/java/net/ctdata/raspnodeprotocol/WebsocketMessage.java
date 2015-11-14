package net.ctdata.raspnodeprotocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.ctdata.common.Messages.Abstract.AbstractMessage;

import java.io.IOException;
import java.util.TimeZone;

public class WebsocketMessage {


    AbstractMessage payload;

    public WebsocketMessage(){

    }

    public WebsocketMessage(AbstractMessage payload){
        this.setPayload(payload);
    }

    public AbstractMessage getPayload() {
        return payload;
    }

    public void setPayload(AbstractMessage payload) {
        this.payload = payload;
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        return mapper.writeValueAsString(this);
    }

    public static WebsocketMessage fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        return mapper.readValue(json, WebsocketMessage.class);
    }

}
