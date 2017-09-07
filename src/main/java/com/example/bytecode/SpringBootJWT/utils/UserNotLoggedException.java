package com.example.bytecode.SpringBootJWT.utils;

/**
 * Custom exception to be launched when the JSON web Token is not found into Http Request Header.
 */
public class UserNotLoggedException extends Exception {

    public UserNotLoggedException(String errorMessage){
        super(errorMessage);
    }
}
