package net.panda.backend.dao.impl;

import net.panda.backend.dao.InstanceDao;
import net.panda.backend.dao.model.TInstance;
import net.panda.core.model.InstanceResult;
import net.panda.core.model.InstanceStatus;
import net.panda.dao.AbstractJdbcDao;
import net.panda.dao.SQLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

@Component
public class InstanceJdbcDao extends AbstractJdbcDao implements InstanceDao {

    private final RowMapper<TInstance> instanceRowMapper = new RowMapper<TInstance>() {

        @Override
        public TInstance mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TInstance(
                    rs.getInt("id"),
                    rs.getInt("pipeline"),
                    SQLUtils.parametersFromDB(rs, "parameters"),
                    SQLUtils.getEnum(InstanceStatus.class, rs, "status"),
                    SQLUtils.getEnum(InstanceResult.class, rs, "result"),
                    rs.getString("result_details")
            );
        }
    };

    @Autowired
    public InstanceJdbcDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Transactional
    public int create(int pipeline, Map<String, String> parameterMap) {
        return dbCreate(
                SQL.INSTANCE_CREATE,
                params("pipeline", pipeline)
                        .addValue("parameters", SQLUtils.parametersToDB(parameterMap))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<TInstance> findStarted() {
        return getJdbcTemplate().query(
                SQL.INSTANCE_STARTED,
                instanceRowMapper
        );
    }
}
