package com.metamafitness.fitnessbackend.exception;

public class ElementAlreadyExistException extends BusinessException{

    public ElementAlreadyExistException() {
        super();
    }

    public ElementAlreadyExistException(String defaultMessage, String key, Object[] args) {
        super(defaultMessage, key, args);
    }

    public ElementAlreadyExistException(String defaultMessage, Throwable cause, String key, Object[] args) {
        super(defaultMessage, cause, key, args);
    }

    public ElementAlreadyExistException(Throwable cause, String key, Object[] args) {
        super(cause, key, args);
    }
}
