package net.panda.backend.dao;

import net.panda.backend.dao.model.TPipeline;
import net.panda.core.model.PipelineCreationForm;

import java.util.List;

public interface PipelineDao {

    List<TPipeline> findAll();

    TPipeline getById(int id);

    int create(String name, String description);
}
