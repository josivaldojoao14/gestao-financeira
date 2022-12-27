package com.cdg.cadernog.services.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String msg) {
        super(msg);
    }
}
