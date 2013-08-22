package net.panda.backend.dao.impl;

import net.panda.backend.dao.PipelineAuthorizationDao;
import net.panda.backend.dao.model.TPipelineAuthorization;
import net.panda.core.model.PipelineRole;
import net.panda.dao.AbstractJdbcDao;
import net.panda.dao.SQLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PipelineAuthorizationJdbcDao extends AbstractJdbcDao implements PipelineAuthorizationDao {

    @Autowired
    public PipelineAuthorizationJdbcDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TPipelineAuthorization> findByPipeline(int pipeline) {
        return getNamedParameterJdbcTemplate().query(
                SQL.PIPELINE_AUTHORIZATION_BY_PIPELINE,
                params("pipeline", pipeline),
                new RowMapper<TPipelineAuthorization>() {

                    @Override
                    public TPipelineAuthorization mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new TPipelineAuthorization(
                                rs.getInt("pipeline"),
                                rs.getInt("account"),
                                SQLUtils.getEnum(PipelineRole.class, rs, "role")
                        );
                    }
                }
        );
    }
}
