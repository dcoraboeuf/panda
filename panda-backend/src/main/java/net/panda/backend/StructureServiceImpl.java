package net.panda.backend;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import net.panda.backend.dao.ParameterDao;
import net.panda.backend.dao.PipelineDao;
import net.panda.backend.dao.model.TParameter;
import net.panda.backend.dao.model.TPipeline;
import net.panda.core.model.ParameterSummary;
import net.panda.core.model.PipelineCreationForm;
import net.panda.core.model.PipelineSummary;
import net.panda.core.security.SecurityRoles;
import net.panda.service.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService {

    private final PipelineDao pipelineDao;
    private final ParameterDao parameterDao;
    private final Function<TPipeline, PipelineSummary> pipelineSummaryFunction = new Function<TPipeline, PipelineSummary>() {
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
    public StructureServiceImpl(PipelineDao pipelineDao, ParameterDao parameterDao) {
        this.pipelineDao = pipelineDao;
        this.parameterDao = parameterDao;
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

    @Override
    @Transactional(readOnly = true)
    public PipelineSummary getPipelineByName(String name) {
        return pipelineSummaryFunction.apply(pipelineDao.getByName(name));
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public PipelineSummary createPipeline(PipelineCreationForm form) {
        int id = pipelineDao.create(form.getName(), form.getDescription());
        return getPipeline(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParameterSummary> getPipelineParameters(int pipeline) {
        return Lists.transform(
                parameterDao.findByPipeline(pipeline),
                new Function<TParameter, ParameterSummary>() {

                    @Override
                    public ParameterSummary apply(TParameter t) {
                        return new ParameterSummary(
                                t.getId(),
                                t.getName(),
                                t.getDescription(),
                                t.getDefaultValue(),
                                t.isOverriddable()
                        );
                    }
                }
        );
    }
}
