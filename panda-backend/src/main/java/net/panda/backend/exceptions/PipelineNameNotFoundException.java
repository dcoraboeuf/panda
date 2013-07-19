package net.panda.backend.exceptions;

import net.panda.core.NotFoundException;

public class PipelineNameNotFoundException extends NotFoundException {
    public PipelineNameNotFoundException(String name) {
        super(name);
    }
}
