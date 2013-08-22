package net.panda.service;

import net.panda.core.model.*;

import java.util.List;

public interface StructureService {

    List<PipelineSummary> getPipelines();

    PipelineSummary getPipeline(int id);

    PipelineSummary createPipeline(PipelineCreationForm form);

    PipelineSummary getPipelineByName(String name);

    List<ParameterSummary> getPipelineParameters(int pipeline);

    ParameterSummary getPipelineParameter(int id);

    ParameterSummary createParameter(int pipeline, ParameterCreationForm form);

    ParameterSummary updateParameter(int pipeline, int parameter, ParameterUpdateForm form);

    void deleteParameter(int pipeline, int parameter);
}
