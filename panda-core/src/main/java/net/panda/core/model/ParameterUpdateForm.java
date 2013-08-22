package net.panda.core.model;

import lombok.Data;

@Data
public class ParameterUpdateForm {

    private final String name;
    private final String description;
    private final String defaultValue;
    private final boolean overriddable;

}
