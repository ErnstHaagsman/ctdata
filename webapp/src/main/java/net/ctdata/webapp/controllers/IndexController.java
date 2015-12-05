package net.ctdata.webapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.ctdata.common.Messages.*;
import net.ctdata.common.Messages.Partial.SensorLastObservation;
import net.ctdata.common.Messages.Partial.SensorMetadata;
import net.ctdata.common.Queue.Listeners.AddedNodesMetadataListener;
import net.ctdata.common.Queue.Listeners.HistoryResponseListener;
import net.ctdata.common.Queue.Listeners.MetadataListener;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.queue.QueueSingleton;
import net.ctdata.webapp.queuelistener.MyAddedNodeRequestListener;
import net.ctdata.webapp.queuelistener.MyObservationListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String MapUI(Model model){
        return "map";
    }

    @RequestMapping("/index")
    public String inde(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "index";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("message", "HELLO!");
        return "page_login";
    }

    @RequestMapping(value="/welcome", method=RequestMethod.GET)
    public String welcome(Model model) {
        model.addAttribute("message", "HELLO!");
        return "welcome";
    }

    MyAddedNodeRequestListener addedNode;
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }


    @RequestMapping(value="/changeFrequency", method=RequestMethod.GET)
    public String changeFrequency(Model model) {
        model.addAttribute("frequency", new Frequency());
        return "changeFrequency";
    }

    @RequestMapping(value="/HistoryRequest", method=RequestMethod.GET)
    public String HistoryRequest(Model model) {
        model.addAttribute("historyreq", new HistReq());
        return "HistoryRequest";
    }
    public LinkedList<AddedNode> addedNodes = new LinkedList<AddedNode>();

    public void setAddedNodes(LinkedList<AddedNode> addedNodes) {
        this.addedNodes = addedNodes;
    }
    @RequestMapping(value="/addedNodes", method=RequestMethod.GET)
    public String addedNodes(final Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException, InterruptedException {
        /*RequestAddedNodes rn = new RequestAddedNodes();
        rn.setRequestId(UUID.randomUUID());
        rn.setUserId("Administrator");
        rn.setInterfaceType("Administrator");
        RabbitMqConnection queueConn = new RabbitMqConnection("amqp://localhost");
        queueConn.SendMessage(rn);*/

        RequestAddedNodes rn = new RequestAddedNodes();
        rn.setRequestId(UUID.randomUUID());
        rn.setUserId("Administrator");
        rn.setInterfaceType("Administrator");
        RabbitMqConnection queueConn = QueueSingleton.getConnection();
        queueConn.SendMessage(rn);
        queueConn.RegisterListener(new AddedNodesMetadataListener() {
            @Override
            public void HandleMessage(AddedNodesMetadata message) {
                AddedNode addedNode = new AddedNode();
                for (RaspberryLastObservation r: message.getRaspberryNodes()) {
                    addedNode.setUrl(r.getNodeURL());

                    for (SensorLastObservation s : r.getSensors()) {

                        addedNode.setSensorName(s.getName());
                        addedNode.setSensorType(s.getType());
                        addedNode.setLastObservation(s.getLastObservation());
                        addedNode.setPollingInterval(s.getPollingInterval());
                        addedNode.setLatitude(s.getLatitude());
                        addedNode.setLongitude(s.getLongitude());
                        addedNodes.add(addedNode);
                        addedNode = new AddedNode();
                    }

                }
                System.out.print("addedNodes"+addedNodes);
                model.addAttribute("addedNodes", addedNodes);

            }

        });
        Thread t = new Thread();
        t.sleep(6000);
        return "addedNodes";
    }

    @RequestMapping(value="/testMap", method=RequestMethod.GET)
    public String Map(final Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException, InterruptedException {

        RequestAddedNodes rn = new RequestAddedNodes();
        rn.setRequestId(UUID.randomUUID());
        rn.setUserId("");
        rn.setInterfaceType("Public");
        RabbitMqConnection queueConn = QueueSingleton.getConnection();
        queueConn.SendMessage(rn);
        queueConn.RegisterListener(new AddedNodesMetadataListener() {
            @Override
            public void HandleMessage(AddedNodesMetadata message) throws JsonProcessingException {
                Observations observations = new Observations();
                String observationsJSON;
                LinkedList<Observations> observationsArrayList = new LinkedList<Observations>();
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

                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                observationsJSON = ow.writeValueAsString(observationsArrayList);
                System.out.print("observationsJSON"+observationsJSON);
                model.addAttribute("observationsJSON", observationsJSON);

            }

        });
        Thread t = new Thread();
        t.sleep(6000);
        return "testMap";
    }

    @RequestMapping(value="/historyresponse", method=RequestMethod.POST)
    public String historyReqSubmit(@ModelAttribute HistReq hr, final Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException, InterruptedException {

        HistoryRequest historyRequest = new HistoryRequest();
        historyRequest.setRaspberryNode(hr.getRaspberryNode());
        historyRequest.setSensor(hr.getSensorID());
        historyRequest.setTimePeriod(hr.getInterval());

        RabbitMqConnection queueConn = QueueSingleton.getConnection();
        queueConn.SendMessage(historyRequest);
        queueConn.RegisterListener(new HistoryResponseListener(UUID.randomUUID()) {
            @Override
            public void HandleMessage(HistoryResponse message) {
                HistoryResponse response = new HistoryResponse();
                response.setRequestId(message.getRequestId());
                response.setRaspberryNode(message.getRaspberryNode());
                response.setSensor(message.getSensor());
                response.setObservations(message.getObservations());
                System.out.print("historyResponse"+response);
                model.addAttribute("historyResponse", response);

            }

        });
        Thread t = new Thread();
        t.sleep(6000);
        return "historyresponse";

    }

    @RequestMapping(value="/greeting", method=RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting, final Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException, InterruptedException {
       // model.addAttribute("greeting", greeting);
        long id = greeting.getId();
        String url = greeting.getUrl();

        AddNode an =new AddNode();
        an.setNodeURL(url);
        an.setUserId("Administrator");
        RabbitMqConnection queueConn = QueueSingleton.getConnection();
        queueConn.SendMessage(an);
      //  MyAddNodeResponseListener anr= new MyAddNodeResponseListener();

        queueConn.RegisterListener(new MetadataListener() {
            @Override
            public void HandleMessage(Metadata message) {
                AddedNode addedNode = new AddedNode();
                for (SensorMetadata s : message.getSensors()) {

            addedNode.setUrl(message.getNodeURL());
            addedNode.setSensorName(s.getName());
            System.out.println(s.getName());
            addedNode.setSensorType(s.getType());
            addedNode.setPollingInterval(s.getPollingInterval());
            addedNode.setLatitude(s.getLatitude());
            addedNode.setLongitude(s.getLongitude());
            addedNodes.add(addedNode);
            System.out.println(addedNode.getNodeID());
            addedNode = new AddedNode();
        }
            //System.out.println(addedNodes.toString());
           // addedNode.setAddedNodes(addedNodes);

                System.out.print("addedNodes"+addedNodes);
                model.addAttribute("addedNodes", addedNodes);
        }

        });
        Thread t = new Thread();
        t.sleep(3000);
        return "result";
    }

    @RequestMapping(value="/changeFrequency", method=RequestMethod.POST)
    public String frequencySubmit(@Valid Frequency frequency, final Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException, InterruptedException {
        model.addAttribute("frequency", frequency);
        UUID raspberryNode = frequency.getRaspberryNode();
        int sensor = frequency.getSensor();
        int frequenc = frequency.getFrequencyN();
        UpdateFrequency an =new UpdateFrequency();
        an.setRaspberryNode(raspberryNode);
        an.setSensor(sensor);
        an.setPollingFrequency(frequenc);
        RabbitMqConnection queueConn = QueueSingleton.getConnection();
        queueConn.SendMessage(an);
        RequestAddedNodes rn = new RequestAddedNodes();
        rn.setRequestId(UUID.randomUUID());
        rn.setUserId("Administrator");
        rn.setInterfaceType("Administrator");
        queueConn.SendMessage(rn);
        queueConn.RegisterListener(new AddedNodesMetadataListener() {
            @Override
            public void HandleMessage(AddedNodesMetadata message) {
                AddedNode addedNode = new AddedNode();
                for (RaspberryLastObservation r: message.getRaspberryNodes()) {
                    addedNode.setUrl(r.getNodeURL());

                    for (SensorLastObservation s : r.getSensors()) {

                        addedNode.setSensorName(s.getName());
                        addedNode.setSensorType(s.getType());
                        addedNode.setLastObservation(s.getLastObservation());
                        addedNode.setPollingInterval(s.getPollingInterval());
                        addedNode.setLatitude(s.getLatitude());
                        addedNode.setLongitude(s.getLongitude());
                        addedNodes.add(addedNode);
                        addedNode = new AddedNode();
                    }

                }
                System.out.print("addedNodes"+addedNodes);
                model.addAttribute("addedNodes", addedNodes);

            }

        });
        Thread t = new Thread();
        t.sleep(6000);
        return "addedNodes";
    }
}
