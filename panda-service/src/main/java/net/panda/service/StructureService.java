package net.panda.service;

import net.panda.core.model.ParameterSummary;
import net.panda.core.model.PipelineCreationForm;
import net.panda.core.model.PipelineSummary;

import java.util.List;

public interface StructureService {

    List<PipelineSummary> getPipelines();

    PipelineSummary getPipeline(int id);

    PipelineSummary createPipeline(PipelineCreationForm form);

    PipelineSummary getPipelineByName(String name);

    List<ParameterSummary> getPipelineParameters(int pipeline);
}
