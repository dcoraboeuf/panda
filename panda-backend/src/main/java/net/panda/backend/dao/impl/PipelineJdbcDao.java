package net.panda.backend.dao.impl;

import net.panda.backend.config.Caches;
import net.panda.backend.dao.PipelineDao;
import net.panda.backend.dao.model.TPipeline;
import net.panda.dao.AbstractJdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PipelineJdbcDao extends AbstractJdbcDao implements PipelineDao {

    private final RowMapper<TPipeline> pipelineRowMapper = new RowMapper<TPipeline>() {
        @Override
        public TPipeline mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TPipeline(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
            );
        }
    };

    @Autowired
    public PipelineJdbcDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(Caches.PIPELINE_LIST)
    public List<TPipeline> findAll() {
        return getJdbcTemplate().query(
                SQL.PIPELINE_ALL,
                pipelineRowMapper
        );
    }
}
