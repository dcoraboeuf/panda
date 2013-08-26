package net.panda.backend.exceptions;

import net.panda.core.InputException;

public class BranchNameAlreadyExistsException extends InputException {
    public BranchNameAlreadyExistsException(String name) {
        super(name);
    }
}
