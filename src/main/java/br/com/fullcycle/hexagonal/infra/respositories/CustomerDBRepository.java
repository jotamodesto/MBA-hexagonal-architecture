package br.com.fullcycle.hexagonal.infra.respositories;

import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.person.Cpf;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.infra.jpa.entities.CustomerEntity;
import br.com.fullcycle.hexagonal.infra.jpa.repositories.CustomerJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter - Database implementation (stub)
@Component
public class CustomerDBRepository implements CustomerRepository {
    private final CustomerJpaRepository customerRepository;

    public CustomerDBRepository(CustomerJpaRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Optional<Customer> customerOfId(CustomerId id) {
        Objects.requireNonNull(id, "CustomerId must not be null");
        return this.customerRepository.findById(UUID.fromString(id.value()))
                .map(CustomerEntity::toCustomer);
    }

    @Override
    public Optional<Customer> customerOfCpf(Cpf cpf) {
        Objects.requireNonNull(cpf, "Cpf must not be null");
        return this.customerRepository.findByCpf(cpf.value())
                .map(CustomerEntity::toCustomer);
    }

    @Override
    public Optional<Customer> customerOfEmail(Email email) {
        Objects.requireNonNull(email, "Email must not be null");
        return this.customerRepository.findByEmail(email.value())
                .map(CustomerEntity::toCustomer);
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        return this.customerRepository.save(CustomerEntity.of(customer)).toCustomer();
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        return this.customerRepository.save(CustomerEntity.of(customer)).toCustomer();
    }

    @Override
    public void deleteAll() {
        this.customerRepository.deleteAll();
    }
}
