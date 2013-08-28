package net.panda.core.model;

import lombok.Data;

@Data
public class BranchParameter {

    private final ParameterSummary def;
    private final String value;

}
