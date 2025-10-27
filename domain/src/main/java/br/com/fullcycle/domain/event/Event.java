package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.DomainEvent;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.partner.PartnerId;
import br.com.fullcycle.domain.person.Name;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Event {
    private static final int ONE = 1;
    private final EventId eventId;
    private final Set<EventTicket> tickets;
    private final Set<DomainEvent> domainEvents;

    private Name name;
    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;

    public Event(EventId eventId, Set<EventTicket> tickets) {
        if (eventId == null) {
            throw new ValidationException("Invalid eventId for the Event");
        }

        this.eventId = eventId;
        this.tickets = tickets != null ? tickets : new HashSet<>(0);
        this.domainEvents = new HashSet<>(2);
    }

    public Event(EventId eventId, String name, String date, Integer totalSpots, PartnerId partnerId, Set<EventTicket> tickets) {
        this(eventId, tickets);
        this.setName(name);
        this.setDate(date);
        this.setTotalSpots(totalSpots);
        this.setPartnerId(partnerId);
    }

    public static Event restore(String eventId, String name, String date, int totalSpots, String partnerId, Set<EventTicket> tickets) {
        return new Event(
                EventId.with(eventId),
                name,
                date,
                totalSpots,
                PartnerId.with(partnerId),
                tickets
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    public static Event newEvent(String name, String date, Integer totalSpots, Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.partnerId(), null);
    }

    public EventTicket reserveTicket(final CustomerId customerId) {
        this.allTickets()
                .stream()
                .filter(et -> Objects.equals(et.customerId(), customerId))
                .findFirst()
                .ifPresent(t -> {
                    throw new ValidationException("Customer already has a ticket for this event");
                });

        if (totalSpots < allTickets().size() + ONE) {
            throw new ValidationException("Event sold out");
        }

        final var newEventTicket = EventTicket.newTicket(this.eventId, customerId, allTickets().size() + ONE);

        this.tickets.add(newEventTicket);
        this.domainEvents.add(new EventTicketReserved(newEventTicket.eventTicketId(), eventId(), customerId));

        return newEventTicket;
    }

    public EventId eventId() {
        return eventId;
    }

    public Name name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public PartnerId partnerId() {
        return partnerId;
    }

    public Set<EventTicket> allTickets() {
        return Collections.unmodifiableSet(tickets);
    }

    public Set<DomainEvent> allDomainEvents() {
        return Collections.unmodifiableSet(domainEvents);
    }

    private void setName(String name) {
        this.name = new Name(name);
    }

    private void setDate(String date) {
        if (date == null) {
            throw new ValidationException("Invalid date for the Event");
        }

        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            throw new ValidationException("Invalid date for the Event");
        }
    }

    private void setTotalSpots(Integer totalSpots) {
        if (totalSpots == null) {
            throw new ValidationException("Total spots cannot be null");
        }

        this.totalSpots = totalSpots;
    }

    private void setPartnerId(PartnerId partnerId) {
        this.partnerId = partnerId;
    }
}
