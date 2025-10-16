package br.com.fullcycle.hexagonal.application.entities;

public record Customer(CustomerId customerId, Name name, Cpf cpf, Email email) {
    public Customer(CustomerId customerId, String name, String cpf, String email) {
        this(customerId, new Name(name), new Cpf(cpf), new Email(email));
    }

    public static Customer newCustomer(String name, String cpf, String email) {
        return new Customer(CustomerId.unique(), name, cpf, email);
    }
}
