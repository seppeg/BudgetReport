package com.cegeka.project.infrastructure;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UnexistingResourceException extends Exception {

    private final String resourceName;
    private final UUID id;

    @Override
    public String getMessage() {
        return "Resource "+resourceName+" with id "+id+" does not exist.";
    }
}
