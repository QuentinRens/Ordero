package com.rensq.ordero.service.exceptions;

public class UnknownResourceException extends OrderoException {


    public UnknownResourceException(String field, String resource) {
        super(String.format("Couldn't find a %s for this %s", resource, field));
    }
}
