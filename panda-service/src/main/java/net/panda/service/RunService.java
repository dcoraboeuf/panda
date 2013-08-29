package net.panda.service;

import net.panda.core.model.InstanceCreationForm;
import net.panda.core.model.InstanceSummary;
import net.panda.core.model.ParameterValueForm;

import java.util.Collection;

public interface RunService {

    InstanceSummary createInstance(int pipeline, int branch, Collection<ParameterValueForm> parameters);

}
