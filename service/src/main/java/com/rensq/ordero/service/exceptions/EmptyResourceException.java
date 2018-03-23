package com.rensq.ordero.service.exceptions;

public class EmptyResourceException extends OrderoException {
    public EmptyResourceException() {
        super("Resource is empty and doesn't support such operation");
    }
}
