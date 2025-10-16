package br.com.fullcycle.hexagonal.application.entities;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public record Cpf(String value) {
    public Cpf {
        if (value == null || value.isBlank() || !value.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            throw new ValidationException("Invalid value of cpf");
        }
    }
}
