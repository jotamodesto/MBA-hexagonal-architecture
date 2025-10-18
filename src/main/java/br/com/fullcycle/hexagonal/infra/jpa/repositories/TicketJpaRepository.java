package br.com.fullcycle.hexagonal.infra.jpa.repositories;

import br.com.fullcycle.hexagonal.infra.jpa.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, UUID> {
}
