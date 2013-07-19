package net.panda.backend;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import net.panda.backend.dao.PipelineDao;
import net.panda.backend.dao.model.TPipeline;
import net.panda.core.model.PipelineSummary;
import net.panda.service.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService {

    private final PipelineDao pipelineDao;
    private final Function<TPipeline,PipelineSummary> pipelineSummaryFunction = new Function<TPipeline, PipelineSummary>() {
        @Override
        public PipelineSummary apply(TPipeline t) {
            return new PipelineSummary(
                    t.getId(),
                    t.getName(),
                    t.getDescription()
            );
        }
    };

    @Autowired
    public StructureServiceImpl(PipelineDao pipelineDao) {
        this.pipelineDao = pipelineDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PipelineSummary> getPipelines() {
        return Lists.transform(
                pipelineDao.findAll(),
                pipelineSummaryFunction
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PipelineSummary getPipeline(int id) {
        return pipelineSummaryFunction.apply(pipelineDao.getById(id));
    }
}
