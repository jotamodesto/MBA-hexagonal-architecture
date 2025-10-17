package br.com.fullcycle.hexagonal.application.domain;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.util.UUID;

public record TicketId(String value) implements CanonicalId {

    public TicketId {
        if (value == null) {
            throw new ValidationException("Invalid empty value for TicketId");
        }
    }

    public static TicketId with(final String value) {
        try {
            return new TicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value for TicketId");
        }
    }

    public static TicketId unique() {
        return new TicketId(java.util.UUID.randomUUID().toString());
    }
}
