package net.panda.backend.dao;

import net.panda.backend.dao.model.TParameter;

import java.util.List;

public interface ParameterDao {
    List<TParameter> findByPipeline(int pipeline);
}
