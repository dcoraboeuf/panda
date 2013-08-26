package net.panda.web.controller;

import net.panda.core.support.MapBuilder;
import net.panda.service.StructureService;
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

    private final StructureService structureService;

    @Autowired
    public GUIController(ErrorHandler errorHandler, StructureService structureService) {
        super(errorHandler);
        this.structureService = structureService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    /**
     * Pipeline page
     */
    @RequestMapping(value = "/pipeline/{pipeline}", method = RequestMethod.GET)
    public ModelAndView pipelineGet(@PathVariable int pipeline) {
        return new ModelAndView("pipeline", "pipeline", structureService.getPipeline(pipeline));
    }

    /**
     * Branch page
     */
    @RequestMapping(value = "/pipeline/{pipeline}/branch/{branch}", method = RequestMethod.GET)
    public ModelAndView pipelineBranchGet(@PathVariable int pipeline, @PathVariable int branch) {
        return new ModelAndView("branch",
                MapBuilder.<String, Object>create()
                        .with("pipeline", structureService.getPipeline(pipeline))
                        .with("branch", structureService.getBranch(branch))
                        .get()
        );
    }

}
