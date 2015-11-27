package net.ctdata.common.Json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.util.TimeZone;

public class MapperSingleton {
    private static ObjectMapper mapper;

    /**
     * Creates a single ObjectMapper for JSON (de)serialization. This way there is a single point of configuration
     * for the ObjectMapper, preventing serialization issues.
     * @return {ObjectMapper}
     */
    public ObjectMapper getMapper(){
        if(mapper == null){
            mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        return mapper;
    }
}
