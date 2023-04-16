package com.whomeow.whomeow.exception;

public class UserException extends Exception{

    public UserException() {
        super("NotUserException");
    }
    public UserException(String msg) {
        super(msg);
    }

}
