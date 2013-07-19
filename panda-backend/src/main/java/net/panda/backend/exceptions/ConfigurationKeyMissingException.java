package net.panda.backend.exceptions;

import net.panda.service.model.ConfigurationKey;
import net.sf.jstring.support.CoreException;

public class ConfigurationKeyMissingException extends CoreException {

    public ConfigurationKeyMissingException(ConfigurationKey key) {
        super(key.name());
    }

}
