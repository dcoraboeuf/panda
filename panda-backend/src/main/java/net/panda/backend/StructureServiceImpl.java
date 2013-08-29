package net.panda.backend;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import net.panda.backend.dao.*;
import net.panda.backend.dao.model.*;
import net.panda.core.model.*;
import net.panda.service.AccountService;
import net.panda.service.StructureService;
import net.panda.service.security.AdminGrant;
import net.panda.service.security.PipelineFunction;
import net.panda.service.security.PipelineGrant;
import net.panda.service.security.PipelineGrantId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService {

    private final AccountService accountService;
    private final PipelineDao pipelineDao;
    private final ParameterDao parameterDao;
    private final BranchDao branchDao;
    private final BranchParameterDao branchParameterDao;
    private final PipelineAuthorizationDao pipelineAuthorizationDao;
    private final Function<TPipeline, PipelineSummary> pipelineSummaryFunction = new Function<TPipeline, PipelineSummary>() {
        @Override
        public PipelineSummary apply(TPipeline t) {
            return new PipelineSummary(t.getId(), t.getName(), t.getDescription());
        }
    };
    private final Function<TBranch, BranchSummary> branchSummaryFunction = new Function<TBranch, BranchSummary>() {

        @Override
        public BranchSummary apply(TBranch t) {
            return new BranchSummary(
                    t.getId(),
                    getPipeline(t.getPipeline()),
                    t.getName(),
                    t.getDescription()
            );
        }
    };
    private final Function<TParameter, ParameterSummary> parameterSummaryFunction = new Function<TParameter, ParameterSummary>() {

        @Override
        public ParameterSummary apply(TParameter t) {
            return new ParameterSummary(t.getId(), t.getName(), t.getDescription(), t.getDefaultValue(),
                    t.isOverriddable());
        }
    };

    @Autowired
    public StructureServiceImpl(AccountService accountService, PipelineDao pipelineDao, ParameterDao parameterDao,
                                BranchDao branchDao, BranchParameterDao branchParameterDao, PipelineAuthorizationDao pipelineAuthorizationDao) {
        this.accountService = accountService;
        this.pipelineDao = pipelineDao;
        this.parameterDao = parameterDao;
        this.branchDao = branchDao;
        this.branchParameterDao = branchParameterDao;
        this.pipelineAuthorizationDao = pipelineAuthorizationDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PipelineSummary> getPipelines() {
        return Lists.transform(pipelineDao.findAll(), pipelineSummaryFunction);
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
    @AdminGrant
    public PipelineSummary createPipeline(PipelineCreationForm form) {
        int id = pipelineDao.create(form.getName(), form.getDescription());
        return getPipeline(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParameterSummary> getPipelineParameters(int pipeline) {
        return Lists.transform(parameterDao.findByPipeline(pipeline), parameterSummaryFunction);
    }

    @Override
    @Transactional(readOnly = true)
    public ParameterSummary getPipelineParameter(int id) {
        return parameterSummaryFunction.apply(parameterDao.getById(id));
    }

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.UPDATE)
    public ParameterSummary createParameter(@PipelineGrantId int pipeline, ParameterCreationForm form) {
        return getPipelineParameter(parameterDao.create(pipeline, form.getName(), form.getDescription(),
                form.getDefaultValue(), form.isOverriddable()));
    }

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.UPDATE)
    public ParameterSummary updateParameter(@PipelineGrantId int pipeline, int parameter, ParameterUpdateForm form) {
        parameterDao.update(parameter, form.getName(), form.getDescription(), form.getDefaultValue(),
                form.isOverriddable());
        return getPipelineParameter(parameter);
    }

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.DELETE)
    public void deleteParameter(@PipelineGrantId int pipeline, int parameter) {
        parameterDao.delete(parameter);
    }

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.UPDATE)
    public Ack updatePipelineAuthorization(@PipelineGrantId int pipeline, int account, PipelineRole role) {
        return parameterDao.updatePipelineAuthorization(pipeline, account, role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PipelineAuthorization> getPipelineAuthorizations(int pipeline) {
        return Lists.transform(pipelineAuthorizationDao.findByPipeline(pipeline),
                new Function<TPipelineAuthorization, PipelineAuthorization>() {

                    @Override
                    public PipelineAuthorization apply(TPipelineAuthorization o) {
                        return new PipelineAuthorization(accountService.getAccountSummary(o.getAccount()), o.getRole());
                    }
                });
    }

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.UPDATE)
    public BranchSummary createBranch(@PipelineGrantId int pipeline, BranchCreationForm form) {
        return getBranch(branchDao.create(pipeline, form.getName(), form.getDescription()));
    }

    @Override
    @Transactional(readOnly = true)
    public BranchSummary getBranch(int branch) {
        return branchSummaryFunction.apply(branchDao.getById(branch));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchSummary> getBranches(int pipeline) {
        return Lists.transform(
                branchDao.findByPipeline(pipeline),
                branchSummaryFunction
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchParameter> getBranchParameters(int pipeline, final int branch) {
        // Default branch
        if (branch == 0) {
            return Lists.transform(
                    getPipelineParameters(pipeline),
                    new Function<ParameterSummary, BranchParameter>() {
                        @Override
                        public BranchParameter apply(ParameterSummary parameter) {
                            return new BranchParameter(
                                    parameter,
                                    null
                            );
                        }
                    }
            );
        }
        // Another branch
        else {
            // Gets the branch
            BranchSummary theBranch = getBranch(branch);
            // Pipeline parameters
            List<ParameterSummary> pipelineParameters = getPipelineParameters(theBranch.getPipelineSummary().getId());
            // Transformation
            return Lists.transform(
                    pipelineParameters,
                    new Function<ParameterSummary, BranchParameter>() {
                        @Override
                        public BranchParameter apply(ParameterSummary parameter) {
                            Optional<TBranchParameter> branchParameter = branchParameterDao.findByBranchAndParameter(
                                    branch,
                                    parameter.getId());
                            return new BranchParameter(
                                    parameter,
                                    branchParameter.isPresent() ? branchParameter.get().getValue() : ""
                            );
                        }
                    }
            );
        }
    }

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.UPDATE)
    public Ack updateBranchParameter(@PipelineGrantId int pipeline, int branch, int parameter, BranchParameterForm form) {
        String value = form.getValue();
        if (StringUtils.isBlank(value)) {
            return branchParameterDao.delete(branch, parameter);
        } else {
            return branchParameterDao.updateOrInsert(branch, parameter, value);
        }
    }
}
