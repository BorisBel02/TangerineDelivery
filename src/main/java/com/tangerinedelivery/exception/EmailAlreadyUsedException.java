package com.tangerinedelivery.exception;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
