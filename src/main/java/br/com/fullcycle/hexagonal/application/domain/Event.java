package br.com.fullcycle.hexagonal.application.domain;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Event {
    private static final int ONE = 1;
    private final EventId eventId;
    private Name name;
    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;
    private Set<EventTicket> tickets;

    public Event(EventId eventId) {
        if (eventId == null) {
            throw new ValidationException("Invalid eventId for the Event");
        }

        this.eventId = eventId;
        this.tickets = new HashSet<>(0);
    }

    public Event(EventId eventId, String name, String date, Integer totalSpots, PartnerId partnerId) {
        this(eventId);
        this.setName(name);
        this.setDate(date);
        this.setTotalSpots(totalSpots);
        this.setPartnerId(partnerId);
    }


    public static Event newEvent(String name, String date, Integer totalSpots, Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.partnerId());
    }

    public Ticket reserveTicket(final CustomerId customerId) {
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

        final var newTicket = Ticket.newTicket(this.eventId, customerId);
        this.tickets.add(new EventTicket(newTicket.ticketId(), this.eventId, customerId, allTickets().size() + ONE));

        return newTicket;
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

    private void setName(String name) {
        this.name = new Name(name);
    }

    private void setDate(String date) {
        if (date == null) {
            throw new ValidationException("Invalid date for the Event");
        }

        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
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
