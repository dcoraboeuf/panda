package net.panda.backend.dao.impl;

import net.panda.backend.dao.ParameterDao;
import net.panda.backend.dao.model.TParameter;
import net.panda.dao.AbstractJdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
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
                    rs.getString("defaultValue"),
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
                "SELECT * FROM PARAMETER WHERE PIPELINE = :pipeline ORDER BY NAME ASC",
                params("pipeline", pipeline),
                parameterRowMapper
        );
    }
}
