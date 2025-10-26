package br.com.fullcycle.infrastructure.respositories;

import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.infrastructure.jpa.entities.EventEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.EventJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter - Database implementation (stub)
@Component
public class EventDBRepository implements EventRepository {
    private final EventJpaRepository eventRepository;

    public EventDBRepository(EventJpaRepository eventRepository) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
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
        return this.eventRepository.save(EventEntity.of(event)).toEvent();
    }

    @Override
    @Transactional
    public Event update(Event event) {
        return this.eventRepository.save(EventEntity.of(event)).toEvent();
    }

    @Override
    public void deleteAll() {
        this.eventRepository.deleteAll();
    }
}
