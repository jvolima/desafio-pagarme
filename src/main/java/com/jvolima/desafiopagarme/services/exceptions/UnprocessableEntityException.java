package com.jvolima.desafiopagarme.services.exceptions;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String msg) {
        super(msg);
    }
}
