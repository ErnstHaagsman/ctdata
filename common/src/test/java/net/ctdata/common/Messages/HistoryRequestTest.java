package net.ctdata.common.Messages;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static net.ctdata.common.TestUtil.AssertSerializes.assertSerializes;
import static org.junit.Assert.*;

public class HistoryRequestTest {

    @Test
    public void sensorMessageSerialization() throws IOException {
        HistoryRequest sut = new HistoryRequest();

        sut.setSensor(5);
        sut.setRaspberryNode(UUID.randomUUID());
        sut.setTimePeriod(new Interval(new DateTime(2011,5,14,11,0,0), new DateTime(2014,6,2,12,32,4)));

        assertSerializes(sut);
    }

}
