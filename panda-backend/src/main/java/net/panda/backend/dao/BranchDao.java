package net.panda.backend.dao;

import net.panda.backend.dao.model.TBranch;

import java.util.List;

public interface BranchDao {

    int create(int pipeline, String name, String description);

    TBranch getById(int branch);

    List<TBranch> findByPipeline(int pipeline);
}
