package br.com.fullcycle.hexagonal.application.usecases.partner;

import br.com.fullcycle.hexagonal.application.repository.InMemoryPartnerRepository;
import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class GetPartnerEntityByIdUseCaseTest {
    private static final String EXPECTED_CNPJ = "12.345.678/0001-00";
    private static final String EXPECTED_NAME = "Disney";
    private static final String EXPECTED_EMAIL = "john.doe@gmail.com";

    PartnerRepository partnerRepository;
    GetPartnerByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        partnerRepository = new InMemoryPartnerRepository();
        useCase = new GetPartnerByIdUseCase(partnerRepository);
    }

    @Test
    @DisplayName("Deve obter um parceiro por id")
    public void testGetById() {
        // given
        final var aPartner = Partner.newPartner(EXPECTED_NAME, EXPECTED_CNPJ, EXPECTED_EMAIL);
        final var expectedId = aPartner.partnerId().value();
        partnerRepository.create(aPartner);

        final var input = new GetPartnerByIdUseCase.Input(expectedId);

        // when
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectedId, output.id());
        Assertions.assertEquals(EXPECTED_CNPJ, output.cnpj());
        Assertions.assertEquals(EXPECTED_EMAIL, output.email());
        Assertions.assertEquals(EXPECTED_NAME, output.name());
    }

    @Test
    @DisplayName("Deve obter vazio quando o parceiro não existe")
    public void testGetByIdEmpty() {
        // given
        final var expectedId = UUID.randomUUID().toString();

        final var input = new GetPartnerByIdUseCase.Input(expectedId);

        // when
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());
    }
}