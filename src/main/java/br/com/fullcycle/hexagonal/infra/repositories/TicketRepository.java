package br.com.fullcycle.hexagonal.infra.repositories;

import br.com.fullcycle.hexagonal.infra.models.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByEventIdAndCustomerId(Long id, Long customerId);
}
