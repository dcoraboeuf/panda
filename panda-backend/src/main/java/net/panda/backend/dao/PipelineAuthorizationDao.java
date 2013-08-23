package net.panda.backend.dao;

import net.panda.backend.dao.model.TPipelineAuthorization;

import java.util.List;

public interface PipelineAuthorizationDao {

    List<TPipelineAuthorization> findByPipeline(int pipeline);

    List<TPipelineAuthorization> findByAccount(int account);
}
