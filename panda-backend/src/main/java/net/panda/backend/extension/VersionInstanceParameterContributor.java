package net.panda.backend.extension;

import net.panda.service.extension.InstanceParameterContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Computes the <code>version</code> parameter for an instance.
 */
@Component
public class VersionInstanceParameterContributor implements InstanceParameterContributor {
    @Override
    public Map<String, String> getAdditionalParameters(int pipeline, int branch, Map<String, String> currentParameters) {
        // FIXME Computes the version
        return null;
    }
}
