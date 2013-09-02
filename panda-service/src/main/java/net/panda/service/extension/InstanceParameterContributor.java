package net.panda.service.extension;

import java.util.Map;

public interface InstanceParameterContributor {

    /**
     * Returns a map of parameters (name --> value) to add to the list
     * of parameters being associated with a new instance.
     *
     * @param pipeline          Pipeline to create the instance for
     * @param branch            Branch to create the instance for (0 for default)
     * @param currentParameters List of current parameters (not null)
     * @return A map of parameters, can be <code>null</code> or empty.
     */
    Map<String, String> getAdditionalParameters(int pipeline, int branch, Map<String, String> currentParameters);

}
