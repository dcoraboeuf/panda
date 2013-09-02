package net.panda.backend.dao;

import net.panda.backend.dao.model.TInstance;

import java.util.Collection;
import java.util.Map;

public interface InstanceDao {

    int create(int pipeline, Map<String, String> parameterMap);

    Collection<TInstance> findStarted();
}
