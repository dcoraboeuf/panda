package net.panda.backend.exceptions;

import net.panda.core.InputException;

public class ParameterNameAlreadyExistException extends InputException {
    public ParameterNameAlreadyExistException(String name) {
        super(name);
    }
}
