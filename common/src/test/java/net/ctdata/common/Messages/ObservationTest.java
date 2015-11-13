package net.ctdata.common.Messages;

import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static net.ctdata.common.TestUtil.AssertSerializes.assertSerializes;

public class ObservationTest {
    @Test
    public void observationSerializes() throws IOException {
        Observation sut = new Observation();
        sut.setSensor(32);
        sut.setRaspberryNode(UUID.randomUUID());
        sut.setLatitude(37.337123);
        sut.setLongitude(-121.881764);
        sut.setObservation(3.14);
        sut.setTime(DateTime.now());

        assertSerializes(sut);
    }
}
