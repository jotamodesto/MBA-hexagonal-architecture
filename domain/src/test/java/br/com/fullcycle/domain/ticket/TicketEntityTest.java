package br.com.fullcycle.domain.ticket;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.ticket.Ticket;
import br.com.fullcycle.domain.event.ticket.TicketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TicketEntityTest {

    @Test
    @DisplayName("Deve instanciar um ticket corretamente")
    public void testCreateTicket() {
        // given
        final var expectedEventId = EventId.unique();
        final var expectedCustomerId = CustomerId.unique();
        final var expectedTicketStatus = TicketStatus.PENDING;

        // when
        final var actualTicket = Ticket.newTicket(expectedEventId, expectedCustomerId);

        // then
        Assertions.assertNotNull(actualTicket.ticketId());
        Assertions.assertNotNull(actualTicket.reservedAt());
        Assertions.assertNull(actualTicket.paidAt());
        Assertions.assertEquals(expectedEventId, actualTicket.eventId());
        Assertions.assertEquals(expectedCustomerId, actualTicket.customerId());
        Assertions.assertEquals(expectedTicketStatus, actualTicket.status());
    }
}