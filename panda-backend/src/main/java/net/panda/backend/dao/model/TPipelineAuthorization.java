package net.panda.backend.dao.model;

import lombok.Data;
import net.panda.core.model.PipelineRole;

@Data
public class TPipelineAuthorization {

    private final int pipeline;
    private final int account;
    private final PipelineRole role;

}
