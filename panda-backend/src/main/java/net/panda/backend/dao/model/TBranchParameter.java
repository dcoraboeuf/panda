package net.panda.backend.dao.model;

import lombok.Data;

@Data
public class TBranchParameter {

    private final int branch;
    private final int parameter;
    private final String value;

}
