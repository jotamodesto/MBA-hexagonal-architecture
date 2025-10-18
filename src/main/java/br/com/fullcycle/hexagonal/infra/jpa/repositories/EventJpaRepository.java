package br.com.fullcycle.hexagonal.infra.jpa.repositories;

import br.com.fullcycle.hexagonal.infra.jpa.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventJpaRepository extends CrudRepository<EventEntity, Long> {

}
