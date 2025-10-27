package br.com.fullcycle.infrastructure.jpa.entities;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventTicket;
import br.com.fullcycle.domain.event.EventTicketId;
import br.com.fullcycle.domain.event.ticket.TicketId;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "EventTicket")
@Table(name = "events_tickets")
public class EventTicketEntity {

    @Id
    private UUID eventTicketId;


    private UUID customerId;

    private int ordering;

    private UUID ticketId;

    @ManyToOne(fetch =  FetchType.LAZY)
    private EventEntity event;

    public EventTicketEntity() {
    }

    public EventTicketEntity(UUID eventTicketId, UUID customerId, int ordering, UUID ticketId, EventEntity event) {
        this.eventTicketId = eventTicketId;
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.ordering = ordering;
        this.event = event;
    }

    public static EventTicketEntity of(final EventEntity event, EventTicket eventTicket) {
        return new EventTicketEntity(
                UUID.fromString(eventTicket.eventTicketId().value()),
                UUID.fromString(eventTicket.customerId().value()),
                eventTicket.ordering(),
                eventTicket.ticketId() != null ? UUID.fromString(eventTicket.ticketId().value()) : null,
                event
        );
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID id) {
        this.ticketId = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEventId(EventEntity event) {
        this.event = event;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public EventTicket toEventTicket() {
        return new EventTicket(
                EventTicketId.with(this.eventTicketId.toString()),
                EventId.with(this.event.getId().toString()),
                CustomerId.with(this.customerId.toString()),
                this.ordering,
                this.ticketId != null ? TicketId.with(this.ticketId.toString()) : null
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventTicketEntity that = (EventTicketEntity) o;
        return Objects.equals(this.eventTicketId, that.eventTicketId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventTicketId);
    }
}
