package com.example.dddlabs;

public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
}
