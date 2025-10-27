package br.com.fullcycle.infrastructure.respositories;

import br.com.fullcycle.domain.DomainEvent;
import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.infrastructure.jpa.entities.EventEntity;
import br.com.fullcycle.infrastructure.jpa.entities.OutboxEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.EventJpaRepository;
import br.com.fullcycle.infrastructure.jpa.repositories.OutboxJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter - Database implementation (stub)
@Component
public class EventDBRepository implements EventRepository {
    private final EventJpaRepository eventRepository;
    private final OutboxJpaRepository outboxRepository;
    private final ObjectMapper mapper;

    public EventDBRepository(final EventJpaRepository eventRepository, final OutboxJpaRepository outboxRepository, final ObjectMapper mapper) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.outboxRepository = outboxRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Event> eventOfId(EventId id) {
        Objects.requireNonNull(id, "EventId must not be null");
        return this.eventRepository.findById(UUID.fromString(id.value()))
                .map(EventEntity::toEvent);
    }

    @Override
    @Transactional
    public Event create(Event event) {

        return save(event);
    }

    @Override
    @Transactional
    public Event update(Event event) {
        return save(event);
    }

    @Override
    public void deleteAll() {
        this.eventRepository.deleteAll();
    }

    private Event save(Event event) {
        this.outboxRepository.saveAll(
                event.allDomainEvents().stream()
                        .map(de -> OutboxEntity.of(de, this::toJSON))
                        .toList());

        return this.eventRepository.save(EventEntity.of(event)).toEvent();
    }

    private String toJSON(DomainEvent domainEvent) {
        try {
            return this.mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
