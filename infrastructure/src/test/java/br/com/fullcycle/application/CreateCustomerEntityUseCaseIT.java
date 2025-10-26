package br.com.fullcycle.application;

import br.com.fullcycle.IntegrationTest;
import br.com.fullcycle.application.customer.CreateCustomerUseCase;
import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class CreateCustomerEntityUseCaseIT {
    public static final String EXPECTED_NAME = "John Doe";
    public static final String EXPECTED_EMAIL = "john.doe@gmail.com";
    private static final String EXPECTED_CPF = "123.456.789-01";

    @Autowired
    private CreateCustomerUseCase useCase;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um cliente")
    public void testCreate() {
        // given

        var createInput = new CreateCustomerUseCase.Input(EXPECTED_CPF, EXPECTED_EMAIL, EXPECTED_NAME);

        // when
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(EXPECTED_CPF, output.cpf());
        Assertions.assertEquals(EXPECTED_EMAIL, output.email());
        Assertions.assertEquals(EXPECTED_NAME, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar cliente com CPF existente")
    public void testCreateWithExistingCPFShouldFail() {
        // given
        final var expectedError = "Customer already exists";

        createCustomer(EXPECTED_CPF, EXPECTED_EMAIL, EXPECTED_NAME);

        final var createInput = new CreateCustomerUseCase.Input(EXPECTED_CPF, EXPECTED_EMAIL, EXPECTED_NAME);

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var expectedError = "Customer already exists";

        createCustomer("987.654.321-09", EXPECTED_EMAIL, EXPECTED_NAME);

        final var createInput = new CreateCustomerUseCase.Input(EXPECTED_CPF, EXPECTED_EMAIL, EXPECTED_NAME);

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    private Customer createCustomer(final String cpf, final String email, final String name) {
        return customerRepository.create(Customer.newCustomer(
                name,
                cpf,
                email
        ));
    }
}