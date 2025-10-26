package br.com.fullcycle.infrastructure.respositories;

import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.partner.PartnerId;
import br.com.fullcycle.domain.partner.PartnerRepository;
import br.com.fullcycle.domain.person.Cnpj;
import br.com.fullcycle.domain.person.Email;
import br.com.fullcycle.infrastructure.jpa.entities.PartnerEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.PartnerJpaRepository;
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

    @Override
    public void deleteAll() {
        this.partnerRepository.deleteAll();
    }
}
