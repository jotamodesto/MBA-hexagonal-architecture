package br.com.fullcycle.hexagonal.infra.configurations;

import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import br.com.fullcycle.hexagonal.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

//    private final CustomerRepository customerRepository;
//    private final EventRepository eventRepository;
//    private final PartnerRepository partnerRepository;
//
//    public UseCaseConfig(final CustomerRepository customerRepository, final EventRepository eventRepository, final PartnerService partnerRepository) {
//        this.customerRepository = Objects.requireNonNull(customerRepository);
//        this.eventRepository = Objects.requireNonNull(eventRepository);
//        this.partnerRepository = Objects.requireNonNull(partnerRepository);
//    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        // TODO: Implement CustomerRepository and inject here
        return new CreateCustomerUseCase(null);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        // TODO: Implement EventRepository and PartnerRepository and inject here
        return new CreateEventUseCase(null, null);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        // TODO: Implement PartnerRepository and inject here
        return new CreatePartnerUseCase(null);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        // TODO: Implement CustomerRepository and inject here
        return new GetCustomerByIdUseCase(null);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        // TODO: Implement PartnerRepository and inject here
        return new GetPartnerByIdUseCase(null);

    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        // TODO: Implement EventRepository and PartnerRepository and inject here
        return new SubscribeCustomerToEventUseCase(null, null, null);
    }
}
