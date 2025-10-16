package br.com.fullcycle.hexagonal.application.entities;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.util.UUID;

public record CustomerId(UUID value) implements CanonicalId {

    public CustomerId {
        if (value == null) {
            throw new ValidationException("Invalid empty value for CustomerId");
        }
    }

    public static CustomerId unique() {
        return new CustomerId(UUID.randomUUID());
    }

    public static CustomerId with(final String value) {
        try {
            return new CustomerId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value for CustomerId");
        }
    }
}
