package net.panda.core.model;

import lombok.Data;

@Data
public class ParameterCreationForm {

    private final String name;
    private final String description;
    private final String defaultValue;
    private final boolean overriddable;

}
