package net.panda.backend.dao.impl;

import net.panda.backend.dao.ParameterDao;
import net.panda.backend.dao.model.TParameter;
import net.panda.backend.exceptions.ParameterNameAlreadyExistException;
import net.panda.core.model.Ack;
import net.panda.dao.AbstractJdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ParameterJdbcDao extends AbstractJdbcDao implements ParameterDao {

    private final RowMapper<TParameter> parameterRowMapper = new RowMapper<TParameter>() {
        @Override
        public TParameter mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TParameter(
                    rs.getInt("id"),
                    rs.getInt("pipeline"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("default_value"),
                    rs.getBoolean("overriddable")
            );
        }
    };

    @Autowired
    public ParameterJdbcDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TParameter> findByPipeline(int pipeline) {
        return getNamedParameterJdbcTemplate().query(
                SQL.PARAMETER_BY_PIPELINE,
                params("pipeline", pipeline),
                parameterRowMapper
        );
    }

    @Override
    @Transactional(readOnly = true)
    public TParameter getById(int id) {
        return getNamedParameterJdbcTemplate().queryForObject(
                SQL.PARAMETER_BY_ID,
                params("id", id),
                parameterRowMapper
        );
    }

    @Override
    @Transactional
    public int create(int pipeline, String name, String description, String defaultValue, boolean overriddable) {
        try {
            return dbCreate(
                    SQL.PARAMETER_CREATE,
                    params("pipeline", pipeline)
                            .addValue("name", name)
                            .addValue("description", description)
                            .addValue("defaultValue", defaultValue)
                            .addValue("overriddable", overriddable)
            );
        } catch (DuplicateKeyException ex) {
            throw new ParameterNameAlreadyExistException(name);
        }
    }

    @Override
    @Transactional
    public Ack update(int parameter, String name, String description, String defaultValue, boolean overriddable) {
        return Ack.one(
                getNamedParameterJdbcTemplate().update(
                        SQL.PARAMETER_UPDATE,
                        params("parameter", parameter)
                                .addValue("name", name)
                                .addValue("description", description)
                                .addValue("defaultValue", defaultValue)
                                .addValue("overriddable", overriddable)
                )
        );
    }
}
