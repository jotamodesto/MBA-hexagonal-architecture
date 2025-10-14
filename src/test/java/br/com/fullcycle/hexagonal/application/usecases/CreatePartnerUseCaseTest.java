package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.infra.models.Partner;
import br.com.fullcycle.hexagonal.infra.services.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

class CreatePartnerUseCaseTest {

    @Test
    @DisplayName("Deve criar um parceiro")
    public void testCreatePartner() {
        // given
        final var expectedCNPJ = "123456798901";
        final var expectedName = "Disney";
        final var expectedEmail = "john.doe@gmail.com";

        var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedName, expectedEmail);

        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        Mockito.when(partnerService.findByCnpj(expectedCNPJ)).thenReturn(Optional.empty());
        Mockito.when(partnerService.findByEmail(expectedEmail)).thenReturn(Optional.empty());
        Mockito.when(partnerService.save(any())).thenAnswer(a -> {
            var partner = a.getArgument(0, Partner.class);
            partner.setId(UUID.randomUUID().getMostSignificantBits());
            return partner;
        });

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar o parceiro com CNPJ existente")
    public void testCreateWithExistingCNPJShouldFail() {
        // given
        final var expectedCNPJ = "123456798901";
        final var expectedName = "Disney";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedError = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedName, expectedEmail);

        final var aPartner = new Partner();
        aPartner.setId(UUID.randomUUID().getMostSignificantBits());
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setName(expectedName);
        aPartner.setEmail(expectedEmail);

        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        Mockito.when(partnerService.findByCnpj(expectedCNPJ)).thenReturn(Optional.of(aPartner));

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var expectedCNPJ = "123456798901";
        final var expectedName = "Disney";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedError = "Partner already exists";

        final var createInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedName, expectedEmail);

        final var aPartner = new Partner();
        aPartner.setId(UUID.randomUUID().getMostSignificantBits());
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setEmail(expectedEmail);
        aPartner.setName(expectedName);

        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        Mockito.when(partnerService.findByEmail(expectedEmail)).thenReturn(Optional.of(aPartner));

        final var useCase = new CreatePartnerUseCase(partnerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}