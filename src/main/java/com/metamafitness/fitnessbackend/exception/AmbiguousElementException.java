package com.metamafitness.fitnessbackend.exception;

public class AmbiguousElementException extends BusinessException{

    public AmbiguousElementException() {
    }

    public AmbiguousElementException(String defaultMessage, String key, Object[] args) {
        super(defaultMessage, key, args);
    }

    public AmbiguousElementException(String defaultMessage, Throwable cause, String key, Object[] args) {
        super(defaultMessage, cause, key, args);
    }

    public AmbiguousElementException(Throwable cause, String key, Object[] args) {
        super(cause, key, args);
    }
}
