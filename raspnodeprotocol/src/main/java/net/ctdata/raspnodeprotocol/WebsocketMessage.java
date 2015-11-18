package net.ctdata.raspnodeprotocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.ctdata.common.Json.MapperSingleton;
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
        ObjectMapper mapper = new MapperSingleton().getMapper();
        return mapper.writeValueAsString(this);
    }

    public static WebsocketMessage fromJson(String json) throws IOException {
        ObjectMapper mapper = new MapperSingleton().getMapper();
        return mapper.readValue(json, WebsocketMessage.class);
    }

}
