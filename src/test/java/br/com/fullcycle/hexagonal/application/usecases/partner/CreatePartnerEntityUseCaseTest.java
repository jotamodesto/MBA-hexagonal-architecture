package br.com.fullcycle.hexagonal.application.usecases.partner;

import br.com.fullcycle.hexagonal.application.repository.InMemoryPartnerRepository;
import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreatePartnerEntityUseCaseTest {
    private static final String EXPECTED_CNPJ = "12.345.678/0001-00";
    private static final String EXPECTED_NAME = "Disney";
    private static final String EXPECTED_EMAIL = "john.doe@gmail.com";

    PartnerRepository partnerRepository;
    CreatePartnerUseCase useCase;

    @BeforeEach
    void setUp() {
        partnerRepository = new InMemoryPartnerRepository();
        useCase = new CreatePartnerUseCase(partnerRepository);
    }


    @Test
    @DisplayName("Deve criar um parceiro")
    public void testCreatePartner() {
        // given
        var createInput = new CreatePartnerUseCase.Input(EXPECTED_CNPJ, EXPECTED_NAME, EXPECTED_EMAIL);

        // when
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(EXPECTED_CNPJ, output.cnpj());
        Assertions.assertEquals(EXPECTED_EMAIL, output.email());
        Assertions.assertEquals(EXPECTED_NAME, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar o parceiro com CNPJ existente")
    public void testCreateWithExistingCNPJShouldFail() {
        // given
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner(EXPECTED_NAME, EXPECTED_CNPJ, EXPECTED_EMAIL);
        partnerRepository.create(aPartner);
        var createInput = new CreatePartnerUseCase.Input(EXPECTED_CNPJ, EXPECTED_NAME, "john@email.com");

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner(EXPECTED_NAME, EXPECTED_CNPJ, EXPECTED_EMAIL);
        partnerRepository.create(aPartner);
        var createInput = new CreatePartnerUseCase.Input("12.345.678/0002-00", EXPECTED_NAME, EXPECTED_EMAIL);

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}