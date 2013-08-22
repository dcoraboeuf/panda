package net.panda.backend.dao;

import net.panda.backend.dao.model.TParameter;
import net.panda.core.model.Ack;

import java.util.List;

public interface ParameterDao {

    List<TParameter> findByPipeline(int pipeline);

    TParameter getById(int id);

    int create(int pipeline, String name, String description, String defaultValue, boolean overriddable);

    Ack update(int parameter, String name, String description, String defaultValue, boolean overriddable);
}
