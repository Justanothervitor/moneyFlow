package com.justanothervitor.api_2.exceptions;

public class InvalidAccountException extends RuntimeException{
    public InvalidAccountException(String message) {
        super(message);
    }
}
