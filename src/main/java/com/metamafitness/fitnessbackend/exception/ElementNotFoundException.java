package com.metamafitness.fitnessbackend.exception;

public class ElementNotFoundException extends BusinessException{

    public ElementNotFoundException() {
        super();
    }

    public ElementNotFoundException(String defaultMessage, String key, Object[] args) {
        super(defaultMessage, key, args);
    }

    public ElementNotFoundException(String defaultMessage, Throwable cause, String key, Object[] args) {
        super(defaultMessage, cause, key, args);
    }

    public ElementNotFoundException(Throwable cause, String key, Object[] args) {
        super(cause, key, args);
    }
}
