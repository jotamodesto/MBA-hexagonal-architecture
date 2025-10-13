package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.models.Customer;
import br.com.fullcycle.hexagonal.models.Event;
import br.com.fullcycle.hexagonal.models.Ticket;
import br.com.fullcycle.hexagonal.models.TicketStatus;
import br.com.fullcycle.hexagonal.services.CustomerService;
import br.com.fullcycle.hexagonal.services.EventService;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubscribeCustomerToEventUseCaseTest {
    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() {
        // given
        final var expectedTicketsSize = 1;
        final long expectedCustomerId = TSID.fast().toLong();
        final long expectedEventId = TSID.fast().toLong();

        final var aEvent = new Event();
        aEvent.setId(expectedEventId);
        aEvent.setName("Disney");
        aEvent.setTotalSpots(10);

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(aEvent.getId(), expectedCustomerId);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(expectedCustomerId)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(expectedEventId)).thenReturn(Optional.of(aEvent));
        when(eventService.findTicketByEventIdAndCustomerId(expectedEventId, expectedCustomerId)).thenReturn(Optional.empty());
        when(eventService.save(any())).thenAnswer(a -> {
            final var event = a.getArgument(0, Event.class);
            Assertions.assertEquals(expectedTicketsSize, event.getTickets().size());
            return event;
        });

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var output = useCase.execute(subscribeInput);

        // then
        Assertions.assertEquals(expectedEventId, output.eventId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento que não existe")
    public void testShouldNotBuyTicketOfEmptyEvent() {
        // given
        final var expectedError = "Event not found";
        final long expectedCustomerId = TSID.fast().toLong();
        final long expectedEventId = TSID.fast().toLong();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(expectedCustomerId)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(expectedEventId)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um cliente que não existe")
    public void testShouldNotBuyTicketOfEmptyCustomer() {
        // given
        final var expectedError = "Customer not found";
        final long expectedCustomerId = TSID.fast().toLong();
        final long expectedEventId = TSID.fast().toLong();

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(expectedCustomerId)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um cliente não deve comprar mais de um ticket para o mesmo evento")
    public void testShouldNotBuyTicketTwiceForTheSameEvent() {
        // given
        final var expectedError = "Email already registered";
        final long expectedCustomerId = TSID.fast().toLong();
        final long expectedEventId = TSID.fast().toLong();

        final var aEvent = new Event();
        aEvent.setId(expectedEventId);
        aEvent.setName("Disney");
        aEvent.setTotalSpots(10);

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(expectedCustomerId)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(expectedEventId)).thenReturn(Optional.of(aEvent));
        when(eventService.findTicketByEventIdAndCustomerId(expectedEventId, expectedCustomerId)).thenReturn(Optional.of(new Ticket()));

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um cliente não deve comprar de um evento esgotado")
    public void testShouldNotBuyTicketOfSoldOutEvent() {
        // given
        final var expectedError = "Event sold out";
        final long expectedCustomerId = TSID.fast().toLong();
        final long expectedEventId = TSID.fast().toLong();

        final var aEvent = new Event();
        aEvent.setId(expectedEventId);
        aEvent.setName("Disney");
        aEvent.setTotalSpots(0);

        final var subscribeInput = new SubscribeCustomerToEventUseCase.Input(expectedEventId, expectedCustomerId);

        // when
        final var customerService = mock(CustomerService.class);
        final var eventService = mock(EventService.class);

        when(customerService.findById(expectedCustomerId)).thenReturn(Optional.of(new Customer()));
        when(eventService.findById(expectedEventId)).thenReturn(Optional.of(aEvent));
        when(eventService.findTicketByEventIdAndCustomerId(expectedEventId, expectedCustomerId)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscribeInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}