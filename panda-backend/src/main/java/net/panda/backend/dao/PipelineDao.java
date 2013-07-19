package net.panda.backend.dao;

import net.panda.backend.dao.model.TPipeline;

import java.util.List;

public interface PipelineDao {

    List<TPipeline> findAll();

    TPipeline getById(int id);
}
