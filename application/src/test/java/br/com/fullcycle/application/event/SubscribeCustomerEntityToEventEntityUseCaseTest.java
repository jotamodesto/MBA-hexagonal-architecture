package br.com.fullcycle.application.event;

import br.com.fullcycle.application.repository.InMemoryCustomerRepository;
import br.com.fullcycle.application.repository.InMemoryEventRepository;
import br.com.fullcycle.application.repository.InMemoryTicketRepository;
import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.event.ticket.TicketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubscribeCustomerEntityToEventEntityUseCaseTest {

    SubscribeCustomerToEventUseCase useCase;

    private InMemoryCustomerRepository customerRepository;
    private InMemoryEventRepository eventRepository;

    private Customer johnDoe;
    private Partner disney;
    private Event disneyOnIce;

    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepository();
        eventRepository = new InMemoryEventRepository();

        useCase = new SubscribeCustomerToEventUseCase(
                customerRepository,
                eventRepository,
                new InMemoryTicketRepository()
        );

        johnDoe = Customer.newCustomer("John Doe", "123.456.789-01", "john.doe@gmail.com");
        disney = Partner.newPartner("Disney", "12.345.678/0001-00", "disney@disney.com");
        disneyOnIce = Event.newEvent("Disney on Ice", "2025-01-01", 10, disney);
    }

    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() {
        // given
        final var expectedTicketsSize = 1;
        final var expectedCustomerId = johnDoe.customerId().value();
        final var expectedEventId = disneyOnIce.eventId().value();


        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        customerRepository.create(johnDoe);
        eventRepository.create(disneyOnIce);

        final var output = useCase.execute(subscribeInput);

        // then
        Assertions.assertEquals(expectedEventId, output.eventId());
        Assertions.assertNotNull(output.ticketId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());

        final var actualEvent = eventRepository.eventOfId(disneyOnIce.eventId());
        Assertions.assertEquals(expectedTicketsSize, actualEvent.get().allTickets().size());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento que não existe")
    public void testShouldNotBuyTicketOfEmptyEvent() {
        // given
        final var expectedError = "Event not found";
        final var expectedCustomerId = johnDoe.customerId().value();
        final var expectedEventId = disneyOnIce.eventId().value();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        customerRepository.create(johnDoe);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um cliente que não existe")
    public void testShouldNotBuyTicketOfEmptyCustomer() {
        // given
        final var expectedError = "Customer not found";
        final var expectedCustomerId = CustomerId.unique().value();
        final var expectedEventId = disneyOnIce.eventId().value();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        eventRepository.create(disneyOnIce);

        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um cliente não deve comprar mais de um ticket para o mesmo evento")
    public void testShouldNotBuyTicketTwiceForTheSameEvent() {
        // given
        final var expectedError = "Customer already has a ticket for this event";
        final var expectedCustomerId = johnDoe.customerId().value();
        final var expectedEventId = disneyOnIce.eventId().value();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        customerRepository.create(johnDoe);
        disneyOnIce.reserveTicket(johnDoe.customerId());
        eventRepository.create(disneyOnIce);

        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um cliente não deve comprar de um evento esgotado")
    public void testShouldNotBuyTicketOfSoldOutEvent() {
        // given
        disneyOnIce = Event.newEvent("Disney on Ice", "2025-01-01", 0, disney);

        final var expectedError = "Event sold out";
        final var expectedCustomerId = johnDoe.customerId().value();
        final var expectedEventId = disneyOnIce.eventId().value();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        customerRepository.create(johnDoe);
        eventRepository.create(disneyOnIce);

        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}