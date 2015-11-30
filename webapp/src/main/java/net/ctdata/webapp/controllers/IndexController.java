package net.ctdata.webapp.controllers;

import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Messages.HistoryRequest;
import net.ctdata.common.Messages.RequestAddedNodes;
import net.ctdata.common.Messages.UpdateFrequency;
import net.ctdata.common.Queue.RabbitMqConnection;
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
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Controller
public class IndexController {
//    @RequestMapping(value = "/")
//    public ModelAndView index() {
//        ModelAndView mav = new ModelAndView("index/index");
//
//        String msg = "Running IndexController.index() method";
//
//        mav.addObject("msg", msg);
//        return mav;
//    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "HELLO!");
        return "hello";
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
        model.addAttribute("greeting", new Greeting());
        return "HistoryRequest";
    }

    @RequestMapping(value="/addedNodes", method=RequestMethod.GET)
    public String addedNodes(Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
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
        RabbitMqConnection queueConn = new RabbitMqConnection("amqp://localhost");
        queueConn.SendMessage(rn);
        MyAddedNodeRequestListener an= new MyAddedNodeRequestListener(UUID.randomUUID(),queueConn);
        //an.setAddNode();

        model.addAttribute("addedNodes", an.getAddedNodes());
        return "addedNodes";
    }

    @RequestMapping(value="/testMap", method=RequestMethod.GET)
    public String Map(Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {

        RequestAddedNodes rn = new RequestAddedNodes();
        rn.setRequestId(UUID.randomUUID());
        rn.setUserId("");
        rn.setInterfaceType("Public");
        RabbitMqConnection queueConn = new RabbitMqConnection("amqp://localhost");
        queueConn.SendMessage(rn);
        MyObservationListener an= new MyObservationListener(UUID.randomUUID(),queueConn);
        //an.setObservations();
        //model.addAttribute("observationsArrayList", an.getObservationsJSON());
        model.addAttribute("observationsJSON", an.getObservationsJSON());
        return "testMap";
    }

    @RequestMapping(value="/history", method=RequestMethod.GET)
    public String History(Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {

        HistoryRequest rn = new HistoryRequest();
        rn.setRaspberryNode(UUID.randomUUID());
        rn.setSensor(9);
        //rn.setInterfaceType("Public");
        RabbitMqConnection queueConn = new RabbitMqConnection("amqp://localhost");
        queueConn.SendMessage(rn);
        MyObservationListener an= new MyObservationListener(UUID.randomUUID(),queueConn);
        //an.setObservations();
        //model.addAttribute("observationsArrayList", an.getObservationsJSON());
        model.addAttribute("observationsJSON", an.getObservationsJSON());
        return "history";
    }

    @RequestMapping(value="/greeting", method=RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
        model.addAttribute("greeting", greeting);
        long id = greeting.getId();
        String url = greeting.getUrl();
        AddNode an =new AddNode();
        an.setNodeURL(url);
        an.setUserId("Administrator");
        RabbitMqConnection queueConn = new RabbitMqConnection("amqp://localhost");
        queueConn.SendMessage(an);
        return "index";
    }

    @RequestMapping(value="/changeFrequency", method=RequestMethod.POST)
    public String frequencySubmit(@Valid Frequency frequency, Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
        model.addAttribute("frequency", frequency);
        UUID raspberryNode = frequency.getRaspberryNode();
        int sensor = frequency.getSensor();
        int frequenc = frequency.getFrequencyN();
        UpdateFrequency an =new UpdateFrequency();
        an.setRaspberryNode(raspberryNode);
        an.setSensor(sensor);
        an.setPollingFrequency(frequenc);
        RabbitMqConnection queueConn = new RabbitMqConnection("amqp://localhost");
        queueConn.SendMessage(an);
        return "changeFrequency";
    }
}
