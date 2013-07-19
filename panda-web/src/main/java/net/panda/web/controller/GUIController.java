package net.panda.web.controller;

import net.panda.web.support.AbstractGUIController;
import net.panda.web.support.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GUIController extends AbstractGUIController {

    @Autowired
    public GUIController(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/pipeline/{name:[a-zA-Z0-9_\\.]+}", method = RequestMethod.GET)
    public ModelAndView pipelineGet(@PathVariable String name) {
        // FIXME Pipeline page
        return new ModelAndView("pipeline");
    }

}
