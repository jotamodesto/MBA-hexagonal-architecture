package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.partner.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

public class EventEntityTest {
    public static final String EXPECTED_NAME = "Disney on Ice";
    public static final String EXPECTED_DATE = "2025-01-01";
    public static final int EXPECTED_TOTAL_SPOTS = 10;

    Partner partner;

    @BeforeEach
    void setUp() {
        partner = Partner.newPartner("Disney", "12.345.678/0001-00", "john.doe@gmail.com");
    }

    @Test
    @DisplayName("Deve instanciar um event corretamente")
    public void testCreateEvent() {
        // given
        final var expectedTickets = 0;

        // when
        final var actualEvent = Event.newEvent(EXPECTED_NAME, EXPECTED_DATE, EXPECTED_TOTAL_SPOTS, partner);

        // then
        Assertions.assertNotNull(actualEvent.eventId());
        Assertions.assertEquals(EXPECTED_NAME, actualEvent.name().value());
        Assertions.assertEquals(EXPECTED_DATE, actualEvent.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertEquals(EXPECTED_TOTAL_SPOTS, actualEvent.totalSpots());
        Assertions.assertEquals(partner.partnerId(), actualEvent.partnerId());
        Assertions.assertEquals(expectedTickets, actualEvent.allTickets().size());
    }

    @Test
    @DisplayName("Não deve instanciar um event com Nome inválido")
    public void testCreateEventWithInvalidName() {
        // given
        final var expectedError = "Invalid value of name";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Event.newEvent(null, EXPECTED_DATE, EXPECTED_TOTAL_SPOTS, partner)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um event com data nula")
    public void testCreateEventWithNullDate() {
        // given
        final var expectedError = "Invalid date for the Event";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Event.newEvent(EXPECTED_NAME, null, EXPECTED_TOTAL_SPOTS, partner)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um event com data inválida")
    public void testCreateEventWithInvalidDate() {
        // given
        final var expectedError = "Invalid date for the Event";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Event.newEvent(EXPECTED_NAME, "20250101", EXPECTED_TOTAL_SPOTS, partner)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um event com spots nulo")
    public void testCreateEventWithNullSpots() {
        // given
        final var expectedError = "Total spots cannot be null";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Event.newEvent(EXPECTED_NAME, EXPECTED_DATE, null, partner)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Deve reservar um ticket")
    public void testShouldReserveATicket() {
        // given
        final var expectedTickets = 1;
        final var expectedTicketOrder = 1;
        final var expectedDomainEventType = "event-ticket.reserved";
        final var expectedCustomerId = CustomerId.unique();

        // when
        final var actualEvent = Event.newEvent(EXPECTED_NAME, EXPECTED_DATE, EXPECTED_TOTAL_SPOTS, partner);
        final var actualTicket = actualEvent.reserveTicket(expectedCustomerId);
        final var actualEventTicket = actualEvent.allTickets().iterator().next();
        final var actualDomainEvent = actualEvent.allDomainEvents().iterator().next();

        // then
        Assertions.assertEquals(expectedTickets, actualEvent.allTickets().size());

        Assertions.assertNotNull(actualTicket.eventTicketId());
        Assertions.assertEquals(actualEvent.eventId(), actualTicket.eventId());
        Assertions.assertEquals(expectedCustomerId, actualTicket.customerId());

        Assertions.assertEquals(expectedTicketOrder, actualEventTicket.ordering());
        Assertions.assertEquals(actualEvent.eventId(), actualEventTicket.eventId());
        Assertions.assertEquals(expectedCustomerId, actualEventTicket.customerId());
        Assertions.assertEquals(actualTicket.ticketId(), actualEventTicket.ticketId());

        Assertions.assertEquals(expectedDomainEventType, actualDomainEvent.type());
    }
}
