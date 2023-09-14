package com.metamafitness.fitnessbackend.exception;

public class UserAlreadyEnrolled extends BusinessException {
    public UserAlreadyEnrolled() {
        super();
    }

    public UserAlreadyEnrolled(String defaultMessage, String key, Object[] args) {
        super(defaultMessage, key, args);
    }

    public UserAlreadyEnrolled(String defaultMessage, Throwable cause, String key, Object[] args) {
        super(defaultMessage, cause, key, args);
    }

    public UserAlreadyEnrolled(Throwable cause, String key, Object[] args) {
        super(cause, key, args);
    }
}
