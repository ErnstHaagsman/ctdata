package net.ctdata.common.Messages;

import net.ctdata.common.Messages.Partial.SensorMetadata;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static net.ctdata.common.TestUtil.AssertSerializes.assertSerializes;

public class MetadataTest {

    @Test
    public void metadataSerializes() throws IOException {
        Metadata sut = new Metadata();
        sut.setRaspberryNode(UUID.randomUUID());

        SensorMetadata s1 = new SensorMetadata();
        s1.setSensor(45);
        s1.setLastObservation(DateTime.now());
        s1.setLatitude(42.2353);
        s1.setLongitude(-123.535);
        s1.setName("Amazingly the random coordinates I picked are somewhere in a forest in NorCal");
        s1.setPollingInterval(5000);
        s1.setType("Tree Leaf Counter");

        List<SensorMetadata> sensors = new LinkedList<SensorMetadata>();
        sensors.add(s1);
        sut.setSensors(sensors);

        assertSerializes(sut);
    }
}
