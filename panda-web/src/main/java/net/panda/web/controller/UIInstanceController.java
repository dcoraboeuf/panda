package net.panda.web.controller;

import net.panda.core.model.InstanceCreationForm;
import net.panda.core.model.InstanceSummary;
import net.panda.service.RunService;
import net.panda.web.support.AbstractUIController;
import net.panda.web.support.ErrorHandler;
import net.sf.jstring.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UIInstanceController extends AbstractUIController {

    private final RunService runService;

    @Autowired
    public UIInstanceController(ErrorHandler errorHandler, Strings strings, RunService runService) {
        super(errorHandler, strings);
        this.runService = runService;
    }

    @RequestMapping(value = "/ui/instance", method = RequestMethod.POST)
    public
    @ResponseBody
    InstanceSummary createInstance(@RequestBody InstanceCreationForm form) {
        return runService.createInstance(
                form.getPipeline(),
                form.getBranch(),
                form.getParameters()
        );
    }
}
