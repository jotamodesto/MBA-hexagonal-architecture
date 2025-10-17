package br.com.fullcycle.hexagonal.application.domain;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.infra.models.TicketStatus;

import java.time.Instant;

public class Ticket {
    private final TicketId ticketId;
    private CustomerId customerId;
    private EventId eventId;
    private TicketStatus ticketStatus;
    private Instant paidAt;
    private Instant reservedAt;

    public Ticket(TicketId ticketId, CustomerId customerId, EventId eventId, TicketStatus ticketStatus, Instant paidAt, Instant reservedAt) {
        if (ticketId == null) {
            throw new ValidationException("Invalid ticketId for the Ticket");
        }

        this.ticketId = ticketId;
        this.setCustomerId(customerId);
        this.setEventId(eventId);
        this.setTicketStatus(ticketStatus);
        this.setPaidAt(paidAt);
        this.setReservedAt(reservedAt);
    }

    public static Ticket newTicket(EventId eventId, CustomerId customerId) {
        return new Ticket(TicketId.unique(), customerId, eventId, TicketStatus.PENDING, null, Instant.now());
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public EventId eventId() {
        return eventId;
    }

    public TicketStatus status() {
        return ticketStatus;
    }

    public Instant paidAt() {
        return paidAt;
    }

    public Instant reservedAt() {
        return reservedAt;
    }

    private void setCustomerId(CustomerId customerId) {
        if (customerId == null) {
            throw new ValidationException("Invalid customerId for the Ticket");
        }

        this.customerId = customerId;
    }

    private void setEventId(EventId eventId) {
        if (eventId == null) {
            throw new ValidationException("Invalid eventId for the Ticket");
        }

        this.eventId = eventId;
    }

    private void setTicketStatus(TicketStatus ticketStatus) {
        if (ticketStatus == null) {
            throw new ValidationException("Invalid status for the Ticket");
        }

        this.ticketStatus = ticketStatus;
    }

    private void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    private void setReservedAt(Instant reservedAt) {
        if (reservedAt == null) {
            throw new ValidationException("Invalid reservedAt for the Ticket");
        }

        this.reservedAt = reservedAt;
    }
}
