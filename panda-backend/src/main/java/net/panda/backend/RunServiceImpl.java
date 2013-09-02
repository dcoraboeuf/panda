package net.panda.backend;

import net.panda.backend.dao.InstanceDao;
import net.panda.core.model.InstanceSummary;
import net.panda.core.model.ParameterValueForm;
import net.panda.service.RunService;
import net.panda.service.StructureService;
import net.panda.service.extension.InstanceParameterContributor;
import net.panda.service.security.PipelineFunction;
import net.panda.service.security.PipelineGrant;
import net.panda.service.security.PipelineGrantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RunServiceImpl implements RunService {

    /**
     * Parameter that contains the name of the branch.
     */
    public static final String BRANCH = "branch";
    /**
     * Default branch
     */
    public static final String BRANCH_DEFAULT = "default";
    private final StructureService structureService;
    private final InstanceDao instanceDao;
    private List<InstanceParameterContributor> instanceParameterContributors;

    @Autowired
    public RunServiceImpl(StructureService structureService, InstanceDao instanceDao) {
        this.structureService = structureService;
        this.instanceDao = instanceDao;
    }

    @Autowired(required = false)
    public void setInstanceParameterContributors(List<InstanceParameterContributor> instanceParameterContributors) {
        this.instanceParameterContributors = instanceParameterContributors;
    }

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.EXECUTE)
    public InstanceSummary createInstance(@PipelineGrantId int pipeline, int branch, Collection<ParameterValueForm> parameters) {
        // Map of parameters
        Map<String, String> parameterMap = toMap(parameters);
        // Branch as parameter
        String branchName = branch == 0 ? BRANCH_DEFAULT : structureService.getBranch(branch).getName();
        parameterMap.put(BRANCH, branchName);
        // Runs the instance parameters contributors
        if (instanceParameterContributors != null) {
            for (InstanceParameterContributor instanceParameterContributor : instanceParameterContributors) {
                Map<String, String> supplementaryParams = instanceParameterContributor.getAdditionalParameters(pipeline, branch, parameterMap);
                if (supplementaryParams != null) {
                    parameterMap.putAll(supplementaryParams);
                }
            }
        }
        // Creates the instance record
        int id = instanceDao.create(pipeline, parameterMap);
        // The instance will be picked up by the 'instance runner'
        // Returns the instance summary
        return new InstanceSummary(id);
    }

    private Map<String, String> toMap(Collection<ParameterValueForm> parameters) {
        Map<String, String> map = new HashMap<>();
        for (ParameterValueForm form : parameters) {
            map.put(
                    structureService.getPipelineParameter(form.getParameter()).getName(),
                    form.getValue()
            );
        }
        return map;
    }
}
