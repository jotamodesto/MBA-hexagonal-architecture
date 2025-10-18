package br.com.fullcycle.hexagonal.infra.jpa.repositories;

import br.com.fullcycle.hexagonal.application.domain.person.Cnpj;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.infra.jpa.entities.PartnerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PartnerJpaRepository extends CrudRepository<PartnerEntity, UUID> {

    Optional<PartnerEntity> findByCnpj(String cnpj);

    Optional<PartnerEntity> findByEmail(String email);
}