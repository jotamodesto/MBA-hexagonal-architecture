package br.com.fullcycle.domain.partner;

import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PartnerEntityTest {
    public static final String EXPECTED_NAME = "Disney";
    public static final String EXPECTED_CNPJ = "12.345.678/0001-00";
    public static final String EXPECTED_EMAIL = "john.doe@gmail.com";

    @Test
    @DisplayName("Deve instanciar um partner corretamente")
    public void testCreatePartner() {
        // when
        final var actualPartner = Partner.newPartner(EXPECTED_NAME, EXPECTED_CNPJ, EXPECTED_EMAIL);

        // then
        Assertions.assertNotNull(actualPartner.partnerId());
        Assertions.assertEquals(EXPECTED_NAME, actualPartner.name().value());
        Assertions.assertEquals(EXPECTED_CNPJ, actualPartner.cnpj().value());
        Assertions.assertEquals(EXPECTED_EMAIL, actualPartner.email().value());
    }

    @Test
    @DisplayName("Não deve instanciar um partner com Id nulo")
    public void testCreatePartnerWithNullId() {
        // given
        final var expectedError = "Invalid partnerId for the Partner";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> new Partner(null, EXPECTED_NAME, EXPECTED_CNPJ, EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um partner com CNPJ nulo")
    public void testCreatePartnerWithNullCNPJ() {
        // given
        final var expectedError = "Invalid value of cnpj";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Partner.newPartner(EXPECTED_NAME, null, EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um partner com CNPJ inválido")
    public void testCreatePartnerWithInvalidCNPJ() {
        // given
        final var expectedError = "Invalid value of cnpj";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Partner.newPartner(EXPECTED_NAME, "12345678910", EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um partner com Nome inválido")
    public void testCreatePartnerWithInvalidName() {
        // given
        final var expectedError = "Invalid value of name";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Partner.newPartner(null, EXPECTED_CNPJ, EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um partner com email nulo")
    public void testCreatePartnerWithNullEmail() {
        // given
        final var expectedError = "Invalid value of email";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Partner.newPartner(EXPECTED_NAME, EXPECTED_CNPJ, null)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um partner com email inválido")
    public void testCreatePartnerWithInvalidEmail() {
        // given
        final var expectedError = "Invalid value of email";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Partner.newPartner(EXPECTED_NAME, EXPECTED_CNPJ, "johndoe.gmail.com")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
