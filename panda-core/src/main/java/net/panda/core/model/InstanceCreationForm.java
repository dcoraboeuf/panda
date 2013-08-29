package net.panda.core.model;

import lombok.Data;

import java.util.List;

@Data
public class InstanceCreationForm {

    private final int pipeline;
    private final int branch;
    private final List<ParameterValueForm> parameters;

}
