package net.ctdata.common.TestUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.ctdata.common.Messages.Abstract.AbstractMessage;

import java.io.IOException;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class AssertSerializes {

    public static void assertSerializes (AbstractMessage sut) throws IOException {
        assertSerializes(null, sut);
    }

    public static void assertSerializes (String message, AbstractMessage sut) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        String json = sut.getBody();

        Object actual = mapper.readValue(json, sut.getClass());
        assertEquals(message, sut, actual);
    }
}
