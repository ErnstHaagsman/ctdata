package net.ctdata.common.Messages;

import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static net.ctdata.common.TestUtil.AssertSerializes.assertSerializes;

public class ConfirmationTest {
    @Test
    public void confirmationSerializes() throws IOException {
        Confirmation sut = new Confirmation();
        sut.setTime(DateTime.now());
        sut.setRaspberryNode(UUID.randomUUID());
        sut.setSensor(42);

        assertSerializes(sut);
    }
}
