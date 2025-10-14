package br.com.fullcycle.hexagonal.infra.repositories;

import br.com.fullcycle.hexagonal.infra.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
