package net.ctdata.webapp.controllers;

import net.ctdata.webapp.queuelistener.MyAddedNodeRequestListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String adminForm(Model model) {
        model.addAttribute("addedNodes", addedNode.getAddedNodes());
        return "admin";
    }

    @RequestMapping(value="/greeting", method=RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("greeting", greeting);
        return "result";
    }
}
