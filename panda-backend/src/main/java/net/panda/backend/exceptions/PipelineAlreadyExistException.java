package net.panda.backend.exceptions;

import net.panda.core.InputException;

public class PipelineAlreadyExistException extends InputException {

    public PipelineAlreadyExistException(String name) {
        super(name);
    }
}
