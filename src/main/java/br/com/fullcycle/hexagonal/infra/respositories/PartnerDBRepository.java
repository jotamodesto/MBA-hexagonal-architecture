package br.com.fullcycle.hexagonal.infra.respositories;

import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.domain.partner.PartnerId;
import br.com.fullcycle.hexagonal.application.domain.person.Cnpj;
import br.com.fullcycle.hexagonal.application.domain.person.Cpf;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import br.com.fullcycle.hexagonal.infra.jpa.entities.CustomerEntity;
import br.com.fullcycle.hexagonal.infra.jpa.entities.PartnerEntity;
import br.com.fullcycle.hexagonal.infra.jpa.repositories.PartnerJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter - Database implementation (stub)
@Component
public class PartnerDBRepository implements PartnerRepository {
    private final PartnerJpaRepository partnerRepository;

    public PartnerDBRepository(PartnerJpaRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Optional<Partner> partnerOfId(PartnerId id) {
        Objects.requireNonNull(id, "PartnerId must not be null");
        return this.partnerRepository.findById(UUID.fromString(id.value()))
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfCNPJ(Cnpj cnpj) {
        Objects.requireNonNull(cnpj, "Cnpj must not be null");
        return this.partnerRepository.findByCnpj(cnpj.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    public Optional<Partner> partnerOfEmail(Email email) {
        Objects.requireNonNull(email, "Email must not be null");
        return this.partnerRepository.findByEmail(email.value())
                .map(PartnerEntity::toPartner);
    }

    @Override
    @Transactional
    public Partner create(Partner customer) {
        return this.partnerRepository.save(PartnerEntity.of(customer)).toPartner();
    }

    @Override
    @Transactional
    public Partner update(Partner customer) {
        return this.partnerRepository.save(PartnerEntity.of(customer)).toPartner();
    }
}
