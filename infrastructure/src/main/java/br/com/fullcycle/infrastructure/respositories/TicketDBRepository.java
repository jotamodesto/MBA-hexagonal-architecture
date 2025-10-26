package br.com.fullcycle.infrastructure.respositories;

import br.com.fullcycle.domain.event.ticket.Ticket;
import br.com.fullcycle.domain.event.ticket.TicketId;
import br.com.fullcycle.domain.event.ticket.TicketRepository;
import br.com.fullcycle.infrastructure.jpa.entities.TicketEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.TicketJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class TicketDBRepository implements TicketRepository {
    private final TicketJpaRepository ticketJpaRepository;

    public TicketDBRepository(TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = Objects.requireNonNull(ticketJpaRepository);
    }

    @Override
    public Optional<Ticket> ticketOfId(TicketId id) {
        return this.ticketJpaRepository.findById(UUID.fromString(id.value()))
                .map(TicketEntity::toTicket);
    }

    @Override
    public Ticket create(Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket)).toTicket();
    }

    @Override
    public Ticket update(Ticket ticket) {
        return this.ticketJpaRepository.save(TicketEntity.of(ticket)).toTicket();
    }

    @Override
    public void deleteAll() {
        this.ticketJpaRepository.deleteAll();
    }
}
