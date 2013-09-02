package net.panda.backend.dao.model;

import lombok.Data;
import net.panda.core.model.InstanceResult;
import net.panda.core.model.InstanceStatus;

import java.util.Map;

@Data
public class TInstance {

    private final int id;
    private final int pipeline;
    private final Map<String,String> parameters;
    private final InstanceStatus status;
    private final InstanceResult result;
    private final String resultDetails;

}
