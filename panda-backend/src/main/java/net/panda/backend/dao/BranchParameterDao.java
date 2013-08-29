package net.panda.backend.dao;

import com.google.common.base.Optional;
import net.panda.backend.dao.model.TBranchParameter;
import net.panda.core.model.Ack;

import java.util.List;

public interface BranchParameterDao {

    List<TBranchParameter> findByBranch(int branch);

    Optional<TBranchParameter> findByBranchAndParameter(int branch, int parameter);

    Ack delete(int branch, int parameter);

    Ack updateOrInsert(int branch, int parameter, String value);
}
