package net.panda.core.model;

import lombok.Data;

@Data
public class ParameterSummary {

    private final int id;
    private final String name;
    private final String description;
    private final String defaultValue;
    private final boolean overriddable;

}
