package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.ticket.TicketId;
import br.com.fullcycle.domain.exceptions.ValidationException;

public class EventTicket {
    private final EventTicketId eventTicketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;
    private TicketId ticketId;

    public EventTicket(EventTicketId eventTicketId, EventId eventId, CustomerId customerId, Integer ordering, TicketId ticketId) {
        if (eventTicketId == null) {
            throw new ValidationException("Invalid ticketId for the EventTicket");
        }

        if (eventId == null) {
            throw new ValidationException("Invalid eventId for the EventTicket");
        }

        if (customerId == null) {
            throw new ValidationException("Invalid customerId for the EventTicket");
        }

        this.eventTicketId = eventTicketId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.setOrdering(ordering);
        this.ticketId = ticketId;
    }

    public static EventTicket newTicket(final EventId eventId, final CustomerId customerId, final int ordering) {
        return new EventTicket(EventTicketId.unique(), eventId, customerId, ordering, null);
    }

    public EventTicket associatedTicket(final TicketId ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public EventTicketId eventTicketId() {
        return eventTicketId;
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
