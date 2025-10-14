package br.com.fullcycle.hexagonal.infra.repositories;

import br.com.fullcycle.hexagonal.infra.models.Partner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PartnerRepository extends CrudRepository<Partner, Long> {

    Optional<Partner> findByCnpj(String cnpj);

    Optional<Partner> findByEmail(String email);
}