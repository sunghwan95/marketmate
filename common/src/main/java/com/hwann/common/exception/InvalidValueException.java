package com.hwann.common.exception;

public class InvalidValueException extends RuntimeException{
    public InvalidValueException(String message){
        super(message);
    }
}