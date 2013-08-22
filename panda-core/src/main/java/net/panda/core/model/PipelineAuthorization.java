package net.panda.core.model;

import lombok.Data;

@Data
public class PipelineAuthorization {

    private final AccountSummary account;
    private final PipelineRole role;

}
