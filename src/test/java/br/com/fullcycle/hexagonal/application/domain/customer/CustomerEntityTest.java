package br.com.fullcycle.hexagonal.application.domain.customer;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerEntityTest {
    public static final String EXPECTED_CPF = "123.456.789-10";
    public static final String EXPECTED_EMAIL = "john.doe@gmail.com";
    public static final String EXPECTED_NAME = "John Doe";

    @Test
    @DisplayName("Deve instanciar um customer")
    public void testCreateCustomer() {
        // when
        final var actualCustomer = Customer.newCustomer(EXPECTED_NAME, EXPECTED_CPF, EXPECTED_EMAIL);

        // then
        Assertions.assertNotNull(actualCustomer.customerId());
        Assertions.assertEquals(EXPECTED_CPF, actualCustomer.cpf().value());
        Assertions.assertEquals(EXPECTED_EMAIL, actualCustomer.email().value());
        Assertions.assertEquals(EXPECTED_NAME, actualCustomer.name().value());
    }

    @Test
    @DisplayName("Não deve instanciar um customer com Id nulo")
    public void testCreateCustomerWithNullId() {
        // given
        final var expectedError = "Invalid customerId for the Customer";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> new Customer(null, EXPECTED_NAME, EXPECTED_CPF, EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um customer com CPF nulo")
    public void testCreateCustomerWithNullCPF() {
        // given
        final var expectedError = "Invalid value of cpf";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Customer.newCustomer(EXPECTED_NAME, null, EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um customer com CPF inválido")
    public void testCreateCustomerWithInvalidCPF() {
        // given
        final var expectedError = "Invalid value of cpf";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Customer.newCustomer(EXPECTED_NAME, "12345678910", EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um customer com Nome inválido")
    public void testCreateCustomerWithInvalidName() {
        // given
        final var expectedError = "Invalid value of name";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Customer.newCustomer(null, EXPECTED_CPF, EXPECTED_EMAIL)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um customer com email nulo")
    public void testCreateCustomerWithNullEmail() {
        // given
        final var expectedError = "Invalid value of email";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Customer.newCustomer(EXPECTED_NAME, EXPECTED_CPF, null)
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve instanciar um customer com email inválido")
    public void testCreateCustomerWithInvalidEmail() {
        // given
        final var expectedError = "Invalid value of email";

        // when
        final var actualError = Assertions.assertThrows(ValidationException.class,
                () -> Customer.newCustomer(EXPECTED_NAME, EXPECTED_CPF, "johndoe.gmail.com")
        );

        // then
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
