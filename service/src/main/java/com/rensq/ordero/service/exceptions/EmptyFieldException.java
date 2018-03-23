package com.rensq.ordero.service.exceptions;

public class EmptyFieldException extends OrderoException {
    public EmptyFieldException() {
        super("Some empty fields have been found...");
    }
}
