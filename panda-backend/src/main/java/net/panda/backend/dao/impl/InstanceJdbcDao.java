package net.panda.backend.dao.impl;

import net.panda.backend.dao.InstanceDao;
import net.panda.dao.AbstractJdbcDao;
import net.panda.dao.SQLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class InstanceJdbcDao extends AbstractJdbcDao implements InstanceDao {

    @Autowired
    public InstanceJdbcDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int create(int pipeline, Map<String, String> parameterMap) {
        return dbCreate(
                SQL.INSTANCE_CREATE,
                params("pipeline", pipeline)
                        .addValue("parameters", SQLUtils.parametersToDB(parameterMap))
        );
    }
}
