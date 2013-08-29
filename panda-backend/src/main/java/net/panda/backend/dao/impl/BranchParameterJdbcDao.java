package net.panda.backend.dao.impl;

import com.google.common.base.Optional;
import net.panda.backend.dao.BranchParameterDao;
import net.panda.backend.dao.model.TBranchParameter;
import net.panda.core.model.Ack;
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
public class BranchParameterJdbcDao extends AbstractJdbcDao implements BranchParameterDao {

    private final RowMapper<TBranchParameter> branchParameterRowMapper = new RowMapper<TBranchParameter>() {
        @Override
        public TBranchParameter mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TBranchParameter(
                    rs.getInt("branch"),
                    rs.getInt("parameter"),
                    rs.getString("value")
            );
        }
    };

    @Autowired
    public BranchParameterJdbcDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TBranchParameter> findByBranch(int branch) {
        return getNamedParameterJdbcTemplate().query(
                SQL.BRANCH_PARAMETER_BY_BRANCH,
                params("branch", branch),
                branchParameterRowMapper
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TBranchParameter> findByBranchAndParameter(int branch, int parameter) {
        return getOptional(
                SQL.BRANCH_PARAMETER_BY_BRANCH_AND_PARAMETER,
                params("branch", branch).addValue("parameter", parameter),
                branchParameterRowMapper
        );
    }

    @Override
    @Transactional
    public Ack delete(int branch, int parameter) {
        return Ack.one(
                getNamedParameterJdbcTemplate().update(
                        SQL.BRANCH_PARAMETER_DELETE,
                        params("branch", branch).addValue("parameter", parameter)
                )
        );
    }

    @Override
    @Transactional
    public Ack updateOrInsert(int branch, int parameter, String value) {
        delete(branch, parameter);
        return Ack.one(
                getNamedParameterJdbcTemplate().update(
                        SQL.BRANCH_PARAMETER_INSERT,
                        params("branch", branch).addValue("parameter", parameter).addValue("value", value)
                )
        );
    }
}
