package net.panda.backend.exceptions;

import net.panda.core.InputException;

public class AccountAlreadyExistException extends InputException {
    public AccountAlreadyExistException(String name) {
        super(name);
    }
}
