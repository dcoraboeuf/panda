package net.panda.core;

public abstract class NotFoundException extends InputException {

    protected NotFoundException(Object... params) {
        super(params);
    }
}
