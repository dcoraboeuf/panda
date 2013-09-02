package net.panda.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class InstanceSummary {

    private final int id;
    private final InstanceStatus status;
    private final InstanceResult result;
    private final String resultDetails;

    public InstanceSummary(int id) {
        this(id, InstanceStatus.CREATED, InstanceResult.NONE, null);
    }

}
