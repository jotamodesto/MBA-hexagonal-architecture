package br.com.fullcycle.hexagonal.application;

import br.com.fullcycle.hexagonal.application.domain.Ticket;
import br.com.fullcycle.hexagonal.application.domain.TicketId;
import br.com.fullcycle.hexagonal.application.repositories.TicketRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryTicketRepository implements TicketRepository {
    private final Map<String, Ticket> tickets;

    public InMemoryTicketRepository() {
        this.tickets = new HashMap<>();
    }

    @Override
    public Optional<Ticket> ticketOfId(TicketId id) {
        return Optional.ofNullable(this.tickets.get(Objects.requireNonNull(id).value()));
    }

    @Override
    public Ticket create(Ticket ticket) {
        this.tickets.put(ticket.ticketId().value(), ticket);

        return ticket;
    }

    @Override
    public Ticket update(Ticket ticket) {
        this.tickets.put(ticket.ticketId().value(), ticket);

        return ticket;
    }
}
