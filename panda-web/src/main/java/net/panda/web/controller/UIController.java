package net.panda.web.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import net.panda.core.model.*;
import net.panda.core.security.SecurityUtils;
import net.panda.service.StructureService;
import net.panda.web.resource.Resource;
import net.panda.web.support.AbstractUIController;
import net.panda.web.support.ErrorHandler;
import net.sf.jstring.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/ui")
public class UIController extends AbstractUIController {

    private final StructureService structureService;
    private final SecurityUtils securityUtils;
    private final Function<PipelineSummary, Resource<PipelineSummary>> pipelineSummaryResourceFn = new Function<PipelineSummary, Resource<PipelineSummary>>() {
        @Override
        public Resource<PipelineSummary> apply(PipelineSummary o) {
            return new Resource<>(o)
                    .withLink(linkTo(methodOn(UIController.class).pipelineGet(o.getId())).withSelfRel())
                    .withLink(linkTo(methodOn(GUIController.class).pipelineGet(o.getId())).withRel(Resource.REL_GUI));
        }
    };
    private final Function<Integer, Function<ParameterSummary, Resource<ParameterSummary>>> parameterSummaryResourceStubFn =
            new Function<Integer, Function<ParameterSummary, Resource<ParameterSummary>>>() {

                @Override
                public Function<ParameterSummary, Resource<ParameterSummary>> apply(final Integer pipelineId) {
                    return new Function<ParameterSummary, Resource<ParameterSummary>>() {

                        @Override
                        public Resource<ParameterSummary> apply(ParameterSummary o) {
                            boolean granted = securityUtils.isGranted("PIPELINE", pipelineId, "UPDATE");
                            return new Resource<>(o)
                                    .withUpdate(granted)
                                    .withDelete(granted);
                            // TODO Link to itself
                        }
                    };
                }
            };
    private final Function<Integer, Function<BranchSummary, Resource<BranchSummary>>> branchSummaryResourceStubFn =
            new Function<Integer, Function<BranchSummary, Resource<BranchSummary>>>() {

                @Override
                public Function<BranchSummary, Resource<BranchSummary>> apply(final Integer pipelineId) {
                    return new Function<BranchSummary, Resource<BranchSummary>>() {

                        @Override
                        public Resource<BranchSummary> apply(BranchSummary o) {
                            boolean granted = securityUtils.isGranted("PIPELINE", pipelineId, "UPDATE");
                            return new Resource<>(o)
                                    .withUpdate(granted)
                                    .withDelete(granted)
                                    .withLink(linkTo(methodOn(UIController.class).pipelineBranchGet(pipelineId, o.getId())).withSelfRel())
                                    .withLink(linkTo(methodOn(GUIController.class).pipelineBranchGet(pipelineId, o.getId())).withRel(Resource.REL_GUI));
                        }
                    };
                }
            };
    private final Function<Integer, Function<BranchParameter, Resource<BranchParameter>>> branchParameterResourceStubFn =
            new Function<Integer, Function<BranchParameter, Resource<BranchParameter>>>() {

                @Override
                public Function<BranchParameter, Resource<BranchParameter>> apply(final Integer pipelineId) {
                    return new Function<BranchParameter, Resource<BranchParameter>>() {

                        @Override
                        public Resource<BranchParameter> apply(BranchParameter o) {
                            return new Resource<>(o)
                                    .withUpdateAndDelete(securityUtils.isGranted("PIPELINE", pipelineId, "UPDATE"));
                        }
                    };
                }
            };

    @Autowired
    public UIController(ErrorHandler errorHandler, Strings strings, StructureService structureService, SecurityUtils securityUtils) {
        super(errorHandler, strings);
        this.structureService = structureService;
        this.securityUtils = securityUtils;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    Resource<String> home() {
        return new Resource<>("home")
                .withLink(linkTo(methodOn(UIController.class).home()).withSelfRel())
                .withLink(linkTo(methodOn(GUIController.class).home()).withRel(Resource.REL_GUI))
                .withLink(linkTo(methodOn(UIController.class).pipelineList()).withRel("pipelineList"));
    }

    @RequestMapping(value = "/pipeline", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Resource<PipelineSummary>> pipelineList() {
        return Lists.transform(
                structureService.getPipelines(),
                pipelineSummaryResourceFn
        );
    }

    @RequestMapping(value = "/pipeline", method = RequestMethod.POST)
    public
    @ResponseBody
    Resource<PipelineSummary> pipelineCreate(@RequestBody PipelineCreationForm form) {
        return pipelineSummaryResourceFn.apply(structureService.createPipeline(form));
    }

    @RequestMapping(value = "/pipeline/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Resource<PipelineSummary> pipelineGet(@PathVariable int id) {
        return pipelineSummaryResourceFn.apply(structureService.getPipeline(id));
    }

    @RequestMapping(value = "/pipeline/{pipeline}/parameter", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Resource<ParameterSummary>> pipelineParameterList(@PathVariable int pipeline) {
        return Lists.transform(
                structureService.getPipelineParameters(pipeline),
                parameterSummaryResourceStubFn.apply(pipeline)
        );
    }

    @RequestMapping(value = "/pipeline/{pipeline}/parameter", method = RequestMethod.POST)
    public
    @ResponseBody
    Resource<ParameterSummary> pipelineParameterCreate(@PathVariable int pipeline, @RequestBody ParameterCreationForm form) {
        return parameterSummaryResourceStubFn.apply(pipeline).apply(
                structureService.createParameter(pipeline, form)
        );
    }

    @RequestMapping(value = "/pipeline/{pipeline}/parameter/{parameter}", method = RequestMethod.GET)
    public
    @ResponseBody
    Resource<ParameterSummary> pipelineParameterGet(@PathVariable int pipeline, @PathVariable int parameter) {
        return parameterSummaryResourceStubFn.apply(pipeline).apply(
                structureService.getPipelineParameter(parameter)
        );
    }

    @RequestMapping(value = "/pipeline/{pipeline}/parameter/{parameter}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Resource<ParameterSummary> pipelineParameterUpdate(@PathVariable int pipeline, @PathVariable int parameter, @RequestBody ParameterUpdateForm form) {
        return parameterSummaryResourceStubFn.apply(pipeline).apply(
                structureService.updateParameter(pipeline, parameter, form)
        );
    }

    @RequestMapping(value = "/pipeline/{pipeline}/parameter/{parameter}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Resource<ParameterSummary> pipelineParameterDelete(@PathVariable int pipeline, @PathVariable int parameter) {
        Resource<ParameterSummary> summary = pipelineParameterGet(pipeline, parameter);
        structureService.deleteParameter(pipeline, parameter);
        return summary;
    }

    @RequestMapping(value = "/pipeline/{pipeline}/branch", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Resource<BranchSummary>> pipelineBranchList(@PathVariable int pipeline) {
        return Lists.transform(
                structureService.getBranches(pipeline),
                branchSummaryResourceStubFn.apply(pipeline)
        );
    }

    @RequestMapping(value = "/pipeline/{pipeline}/branch", method = RequestMethod.POST)
    public
    @ResponseBody
    Resource<BranchSummary> pipelineBranchCreate(@PathVariable int pipeline, @RequestBody BranchCreationForm form) {
        return branchSummaryResourceStubFn.apply(pipeline).apply(
                structureService.createBranch(pipeline, form)
        );
    }

    @RequestMapping(value = "/pipeline/{pipeline}/branch/{branch}", method = RequestMethod.GET)
    public
    @ResponseBody
    Resource<BranchSummary> pipelineBranchGet(@PathVariable int pipeline, @PathVariable int branch) {
        return branchSummaryResourceStubFn.apply(pipeline).apply(
                structureService.getBranch(branch)
        );
    }

    /**
     * Gets the list of parameters for a branch
     */
    @RequestMapping(value = "/pipeline/{pipeline}/branch/{branch}/parameter", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Resource<BranchParameter>> pipelineBranchParameterList(@PathVariable int pipeline, @PathVariable int branch) {
        return Lists.transform(
                structureService.getBranchParameters(pipeline, branch),
                branchParameterResourceStubFn.apply(pipeline)
        );
    }

    /**
     * Updates a parameter for a branch
     */
    @RequestMapping(value = "/pipeline/{pipeline}/branch/{branch}/parameter/{parameter}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Ack pipelineBranchParameterList(@PathVariable int pipeline, @PathVariable int branch, @PathVariable int parameter, @RequestBody BranchParameterForm form) {
        return structureService.updateBranchParameter(pipeline, branch, parameter, form);
    }

    /**
     * Gets the list of authorizations for a pipeline
     */
    @RequestMapping(value = "/pipeline/{pipeline}/authorization", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Resource<PipelineAuthorization>> pipelineAuthorizationList(@PathVariable int pipeline) {
        return Lists.transform(
                structureService.getPipelineAuthorizations(pipeline),
                new Function<PipelineAuthorization, Resource<PipelineAuthorization>>() {

                    @Override
                    public Resource<PipelineAuthorization> apply(PipelineAuthorization o) {
                        return new Resource<>(o);
                    }
                }
        );
    }

    @RequestMapping(value = "/pipeline/{pipeline}/authorization/{account}/{role}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Ack pipelineAuthorizationUpdate(@PathVariable int pipeline, @PathVariable int account, @PathVariable PipelineRole role) {
        return structureService.updatePipelineAuthorization(pipeline, account, role);
    }
}
