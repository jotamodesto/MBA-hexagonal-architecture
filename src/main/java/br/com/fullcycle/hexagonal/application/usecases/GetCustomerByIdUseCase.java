package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.entities.CustomerId;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;

import java.util.Optional;

public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {
    private final CustomerRepository customerRepository;

    public GetCustomerByIdUseCase(final CustomerRepository customerService) {
        this.customerRepository = customerService;
    }

    @Override
    public Optional<Output> execute(Input input) {
        return customerRepository.customerOfId(CustomerId.with(input.id()))
                .map(
                        c -> new Output(
                                c.customerId().value().toString(),
                                c.cpf().value(),
                                c.email().value(),
                                c.name().value()
                        )
                );
    }

    public record Input(String id) {
    }

    public record Output(String id, String cpf, String email, String name) {
    }
}
