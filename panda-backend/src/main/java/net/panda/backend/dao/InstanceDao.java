package net.panda.backend.dao;

import java.util.Map;

public interface InstanceDao {

    int create(int pipeline, Map<String, String> parameterMap);

}
