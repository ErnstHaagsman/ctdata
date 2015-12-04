package net.ctdata.webapp.queuelistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.ctdata.common.Messages.HistoryResponse;
import net.ctdata.common.Messages.Observation;
import net.ctdata.common.Queue.Listeners.HistoryResponseListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.controllers.Observations;
import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by dhaval on 28-11-2015.
 */
public class MyHistoryResponseListener extends HistoryResponseListener{

    private RabbitMqConnection queueConn;
    LinkedList<Observation> obsLinkList = new LinkedList<Observation>();
    HistoryResponse response = new HistoryResponse();
    String responseJSON;

    public MyHistoryResponseListener(RabbitMqConnection queueConn){
        super(UUID.randomUUID());
        this.queueConn = queueConn;
    }

    public HistoryResponse getHistoryResponse() {
        return response;
    }

    public void setHistoryResponse() {

        //HistoryResponse resp = new HistoryResponse();
        Observation obs = new Observation();


        response.setRaspberryNode(UUID.randomUUID());
        response.setSensor(333);
        response.setRequestId(UUID.randomUUID());

        //add one more observation
        obs.setRaspberryNode(response.getRaspberryNode());
        obs.setSensor(response.getSensor());
        obs.setLatitude(37.332);
        obs.setLongitude(-121.889555);
        obs.setObservation(33.3);
        obs.setTime(new DateTime());
        obsLinkList.add(obs);

        obs = new Observation();

        obs.setRaspberryNode(response.getRaspberryNode());
        obs.setSensor(response.getSensor());
        obs.setLatitude(37.33181);
        obs.setLongitude(-121.901206);
        obs.setObservation(7.8);
        obs.setTime(new DateTime());
        obsLinkList.add(obs);

        obs = new Observation();

        obs.setRaspberryNode(response.getRaspberryNode());
        obs.setSensor(response.getSensor());
        obs.setLatitude(37.323144);
        obs.setLongitude(-121.94618);
        obs.setObservation(7.8);
        obs.setTime(new DateTime());
        obsLinkList.add(obs);

        obs = new Observation();

        obs.setRaspberryNode(response.getRaspberryNode());
        obs.setSensor(response.getSensor());
        obs.setLatitude(37.2993);
        obs.setLongitude(-121.90210);
        obs.setObservation(7.8);
        obs.setTime(new DateTime());
        obsLinkList.add(obs);

        obs = new Observation();

        obs.setRaspberryNode(response.getRaspberryNode());
        obs.setSensor(response.getSensor());
        obs.setLatitude(37.33590);
        obs.setLongitude(-121.90026);
        obs.setObservation(7.8);
        obs.setTime(new DateTime());
        obsLinkList.add(obs);

        obs = new Observation();

        obs.setRaspberryNode(response.getRaspberryNode());
        obs.setSensor(response.getSensor());
        obs.setLatitude(37.33512);
        obs.setLongitude(-121.88554);
        obs.setObservation(7.8);
        obs.setTime(new DateTime());
        obsLinkList.add(obs);

        response.setObservations(obsLinkList);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            responseJSON = ow.writeValueAsString(obsLinkList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("json string : "+ responseJSON);

    }

    @Override
    public void HandleMessage(HistoryResponse message) {

        response.setRequestId(message.getRequestId());
        response.setRaspberryNode(message.getRaspberryNode());
        response.setSensor(message.getSensor());
        response.setObservations(message.getObservations());

    }
}
