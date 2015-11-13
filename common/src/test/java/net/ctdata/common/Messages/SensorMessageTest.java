package net.ctdata.common.Messages;

import net.ctdata.common.Messages.Abstract.SensorMessage;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static net.ctdata.common.TestUtil.AssertSerializes.assertSerializes;

public class SensorMessageTest {
    @Test
    public void sensorMessageSerialization() throws IOException {
        SensorMessage sut = new SensorMessage();

        sut.setSensor(5);
        sut.setRaspberryNode(UUID.randomUUID());

        assertSerializes(sut);
    }
}
