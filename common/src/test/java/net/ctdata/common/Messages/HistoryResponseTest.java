package net.ctdata.common.Messages;

import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static net.ctdata.common.TestUtil.AssertSerializes.assertSerializes;

public class HistoryResponseTest {
    
    @Test
    public void historyResponseSerializes() throws IOException {
        int sensor = 15;
        UUID raspberryNode = UUID.randomUUID();

        Observation obs1 = new Observation();
        obs1.setRaspberryNode(raspberryNode);
        obs1.setSensor(sensor);
        obs1.setLatitude(37.337123);
        obs1.setLongitude(-121.881764);
        obs1.setObservation(3.14);
        obs1.setTime(DateTime.now());

        Observation obs2 = new Observation();
        obs2.setSensor(sensor);
        obs2.setRaspberryNode(raspberryNode);
        obs2.setLatitude(37.125);
        obs2.setLongitude(-121.353);
        obs2.setObservation(42);
        obs2.setTime(DateTime.now());

        HistoryResponse sut = new HistoryResponse();
        sut.setRaspberryNode(raspberryNode);
        sut.setSensor(sensor);
        sut.setRequestId(UUID.randomUUID());
        List<Observation> observations = new LinkedList<Observation>();
        observations.add(obs1);
        observations.add(obs2);
        sut.setObservations(observations);

        assertSerializes(sut);
    }
}
