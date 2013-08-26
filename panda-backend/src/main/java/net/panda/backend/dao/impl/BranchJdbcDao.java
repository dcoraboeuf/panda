package net.panda.backend.dao.impl;

import net.panda.backend.dao.BranchDao;
import net.panda.backend.dao.model.TBranch;
import net.panda.backend.exceptions.BranchNameAlreadyExistsException;
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
public class BranchJdbcDao extends AbstractJdbcDao implements BranchDao {

    private final RowMapper<TBranch> branchRowMapper = new RowMapper<TBranch>() {
        @Override
        public TBranch mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TBranch(
                    rs.getInt("id"),
                    rs.getInt("pipeline"),
                    rs.getString("name"),
                    rs.getString("description")
            );
        }
    };

    @Autowired
    public BranchJdbcDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Transactional
    public int create(int pipeline, String name, String description) {
        try {
            return dbCreate(
                    SQL.BRANCH_CREATE,
                    params("pipeline", pipeline).addValue("name", name).addValue("description", description)
            );
        } catch (DuplicateKeyException ex) {
            throw new BranchNameAlreadyExistsException(name);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TBranch getById(int branch) {
        return getNamedParameterJdbcTemplate().queryForObject(
                SQL.BRANCH_BY_ID,
                params("id", branch),
                branchRowMapper
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TBranch> findByPipeline(int pipeline) {
        return getNamedParameterJdbcTemplate().query(
                SQL.BRANCH_BY_PIPELINE,
                params("pipeline", pipeline),
                branchRowMapper
        );
    }
}
