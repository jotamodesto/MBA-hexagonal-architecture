package br.com.fullcycle.domain.customer;

import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.person.Cpf;
import br.com.fullcycle.domain.person.Email;
import br.com.fullcycle.domain.person.Name;

public record Customer(CustomerId customerId, Name name, Cpf cpf, Email email) {
    public Customer {
        if (customerId == null) {
            throw new ValidationException("Invalid customerId for the Customer");
        }

//        if (name == null) {
//            throw new ValidationException("Invalid name for the Customer");
//        }
//
//        if (cpf == null) {
//            throw new ValidationException("Invalid cpf for the Customer");
//        }
//
//        if (email == null) {
//            throw new ValidationException("Invalid email for the Customer");
//        }
    }

    public Customer(CustomerId customerId, String name, String cpf, String email) {
        this(customerId, new Name(name), new Cpf(cpf), new Email(email));
    }

    public static Customer newCustomer(String name, String cpf, String email) {
        return new Customer(CustomerId.unique(), name, cpf, email);
    }
}
