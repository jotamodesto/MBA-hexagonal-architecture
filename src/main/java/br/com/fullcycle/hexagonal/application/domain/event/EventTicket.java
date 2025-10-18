package br.com.fullcycle.hexagonal.application.domain.event;

import br.com.fullcycle.hexagonal.application.domain.ticket.TicketId;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.util.Objects;

public class EventTicket {
    private final TicketId ticketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;

    protected EventTicket(TicketId ticketId, EventId eventId, CustomerId customerId, Integer ordering) {
        if (ticketId == null) {
            throw new ValidationException("Invalid ticketId for the EventTicket");
        }

        if (eventId == null) {
            throw new ValidationException("Invalid eventId for the EventTicket");
        }

        if (customerId == null) {
            throw new ValidationException("Invalid customerId for the EventTicket");
        }

        this.ticketId = ticketId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.setOrdering(ordering);
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public EventId eventId() {
        return eventId;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public int ordering() {
        return ordering;
    }

    private void setOrdering(Integer ordering) {
        if (ordering == null) {
            throw new ValidationException("Invalid ordering for the EventTicket");
        }

        this.ordering = ordering;
    }
}
