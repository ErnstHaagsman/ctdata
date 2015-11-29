package net.ctdata.webapp.controllers;

import net.ctdata.common.Messages.AddNode;
import net.ctdata.common.Messages.RequestAddedNodes;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.queuelistener.MyAddedNodeRequestListener;
import net.ctdata.webapp.queuelistener.MyObservationListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
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

    MyAddedNodeRequestListener addedNode;
    @RequestMapping(value="/greeting", method=RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String adminForm(Model model) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
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
        an.setAddNode();

        model.addAttribute("addedNodes", an.getAddedNodes());
        return "admin";
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
        an.setObservations();
        //model.addAttribute("observationsArrayList", an.getObservationsJSON());
        model.addAttribute("observationsJSON", an.getObservationsJSON());
        return "testMap";
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
        return "result";
    }

}
