package br.com.fullcycle.domain.person;

import br.com.fullcycle.domain.exceptions.ValidationException;

public record Cpf(String value) {
    public Cpf {
        if (value == null || value.isBlank() || !value.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            throw new ValidationException("Invalid value of cpf");
        }
    }

    public static Cpf from(final String value) {
        return new Cpf(value);
    }
}
