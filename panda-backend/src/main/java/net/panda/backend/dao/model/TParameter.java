package net.panda.backend.dao.model;

import lombok.Data;

@Data
public class TParameter {

    private final int id;
    private final int pipeline;
    private final String name;
    private final String description;
    private final String defaultValue;
    private final boolean overriddable;
}
