package net.panda.web.controller;

import net.panda.web.support.AbstractGUIController;
import net.panda.web.support.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GUIController extends AbstractGUIController {

    @Autowired
    public GUIController(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

}
