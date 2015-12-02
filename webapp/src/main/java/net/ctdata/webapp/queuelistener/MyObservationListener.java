package net.ctdata.webapp.queuelistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.ctdata.common.Messages.AddedNodesMetadata;
import net.ctdata.common.Messages.Partial.SensorLastObservation;
import net.ctdata.common.Messages.RaspberryLastObservation;
import net.ctdata.common.Queue.Listeners.AddedNodesMetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.controllers.Observations;

import java.util.LinkedList;
import java.util.UUID;
/**
 * Created by dhaval on 28-11-2015.
 */
public class MyObservationListener extends AddedNodesMetadataListener {

    private RabbitMqConnection queueConn;
    Observations observations = new Observations();
    String observationsJSON;

    LinkedList<Observations> observationsArrayList = new LinkedList<Observations>();

    public MyObservationListener(RabbitMqConnection queueConn){
        super();
        this.queueConn = queueConn;
    }

    public MyObservationListener(UUID requestId, RabbitMqConnection queueConn){
        super(requestId);
        this.queueConn = queueConn;
    }

    public LinkedList<Observations> getObservations() {
        return observationsArrayList;
    }

    public String getObservationsJSON() {
        return observationsJSON;
    }

    public void setObservations() throws JsonProcessingException {

        Observations observations = new Observations();
        observations.setRaspberryNode(UUID.randomUUID());
        observations.setSensorId(666);
        observations.setObservationData(23.424);
        observations.setLatitude(36.485);
        observations.setLongitude(-121.845);
        observationsArrayList.add(observations);

        //add one more observation
        observations = new Observations();
        observations.setRaspberryNode(UUID.randomUUID());
        observations.setSensorId(777);
        observations.setObservationData(34.878);
        observations.setLatitude(36.555);
        observations.setLongitude(-121.666);
        observationsArrayList.add(observations);

        //add one more observation
        observations = new Observations();
        observations.setRaspberryNode(UUID.randomUUID());
        observations.setSensorId(999);
        observations.setObservationData(37.99);
        observations.setLatitude(36.999);
        observations.setLongitude(-121.987);
        observationsArrayList.add(observations);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        observationsJSON = ow.writeValueAsString(observationsArrayList);

        System.out.println("json string : "+ observationsJSON);

    }

    @Override
    public void HandleMessage(AddedNodesMetadata message) {
        System.out.println("Observations");
        System.out.println(String.format("Received metadata for node %s", message.getRaspberryNodes()));
        Observations observations = new Observations();
        for (RaspberryLastObservation r: message.getRaspberryNodes()) {

            observations.setRaspberryNode(r.getRaspberryNode());
            for (SensorLastObservation s : r.getSensors()) {
                observations.setSensorId(s.getSensor());
                observations.setObservationData(s.getLastObservation());
                observations.setLatitude(s.getLatitude());
                observations.setLongitude(s.getLongitude());
                observationsArrayList.add(observations);
                observations = new Observations();
            }
        }
    }
}
