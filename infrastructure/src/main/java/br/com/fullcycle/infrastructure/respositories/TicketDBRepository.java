package br.com.fullcycle.infrastructure.respositories;

import br.com.fullcycle.domain.DomainEvent;
import br.com.fullcycle.domain.event.ticket.Ticket;
import br.com.fullcycle.domain.event.ticket.TicketId;
import br.com.fullcycle.domain.event.ticket.TicketRepository;
import br.com.fullcycle.infrastructure.jpa.entities.OutboxEntity;
import br.com.fullcycle.infrastructure.jpa.entities.TicketEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.OutboxJpaRepository;
import br.com.fullcycle.infrastructure.jpa.repositories.TicketJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class TicketDBRepository implements TicketRepository {
    private final TicketJpaRepository ticketJpaRepository;
    private final OutboxJpaRepository outboxRepository;
    private final ObjectMapper mapper;

    public TicketDBRepository(TicketJpaRepository ticketJpaRepository, OutboxJpaRepository outboxRepository, ObjectMapper mapper) {
        this.ticketJpaRepository = Objects.requireNonNull(ticketJpaRepository);
        this.outboxRepository = Objects.requireNonNull(outboxRepository);
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Override
    public Optional<Ticket> ticketOfId(TicketId id) {
        return this.ticketJpaRepository.findById(UUID.fromString(id.value()))
                .map(TicketEntity::toTicket);
    }

    @Override
    public Ticket create(Ticket ticket) {
        return save(ticket);
    }

    @Override
    public Ticket update(Ticket ticket) {
        return save(ticket);
    }

    @Override
    public void deleteAll() {
        this.ticketJpaRepository.deleteAll();
    }

    private Ticket save(Ticket ticket) {
        this.outboxRepository.saveAll(
                ticket.allDomainEvents().stream()
                        .map(de -> OutboxEntity.of(de, this::toJSON))
                        .toList());

        return this.ticketJpaRepository.save(TicketEntity.of(ticket)).toTicket();
    }

    private String toJSON(DomainEvent domainEvent) {
        try {
            return this.mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
