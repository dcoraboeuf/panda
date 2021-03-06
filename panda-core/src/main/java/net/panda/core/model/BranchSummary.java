package net.panda.core.model;

import lombok.Data;

@Data
public class BranchSummary {

    private final int id;
    private final PipelineSummary pipelineSummary;
    private final String name;
    private final String description;

}
