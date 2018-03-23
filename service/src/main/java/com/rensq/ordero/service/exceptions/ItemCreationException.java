package com.rensq.ordero.service.exceptions;

public class ItemCreationException extends OrderoException {
    public ItemCreationException() {
        super("An item with this name already exist in database");
    }
}
