package net.panda.backend;

import net.panda.core.model.InstanceSummary;
import net.panda.core.model.ParameterValueForm;
import net.panda.service.RunService;
import net.panda.service.security.PipelineFunction;
import net.panda.service.security.PipelineGrant;
import net.panda.service.security.PipelineGrantId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class RunServiceImpl implements RunService {

    @Override
    @Transactional
    @PipelineGrant(PipelineFunction.EXECUTE)
    public InstanceSummary createInstance(@PipelineGrantId int pipeline, int branch, Collection<ParameterValueForm> parameters) {
        // TODO Branch as parameter
        // TODO Creates the instance record
        // The instance will be picked up by the 'instance runner'
        // TODO Returns the instance summary
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
