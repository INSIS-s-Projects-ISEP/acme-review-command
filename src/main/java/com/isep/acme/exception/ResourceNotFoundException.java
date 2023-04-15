package com.isep.acme.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String string) {
        super(string);
    }

    public ResourceNotFoundException(Class<?> clazz, UUID id) {
        super(String.format("Entity %s with id %d not found", clazz.getSimpleName(), id));
    }
}
