package br.com.fullcycle.hexagonal.application.entities;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.util.UUID;

public record PartnerId(UUID value) implements CanonicalId {

    public PartnerId {
        if (value == null) {
            throw new ValidationException("Invalid empty value for PartnerId");
        }
    }

    public static PartnerId unique() {
        return new PartnerId(UUID.randomUUID());
    }

    public static PartnerId with(final String value) {
        try {
            return new PartnerId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value for PartnerId");
        }
    }

}
