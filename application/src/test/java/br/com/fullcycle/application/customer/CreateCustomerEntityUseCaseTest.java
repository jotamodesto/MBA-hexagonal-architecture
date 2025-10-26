package br.com.fullcycle.application.customer;

import br.com.fullcycle.application.repository.InMemoryCustomerRepository;
import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CreateCustomerEntityUseCaseTest {
    private static final String EXPECTED_CPF = "123.456.789-01";
    private static final String EXPECTED_EMAIL = "john.doe@gmail.com";
    private static final String EXPECTED_NAME = "John Doe";


    @Test
    @DisplayName("Deve criar um cliente")
    public void testCreate() {
        // given
        var createInput = new CreateCustomerUseCase.Input(EXPECTED_CPF, EXPECTED_EMAIL, EXPECTED_NAME);
        final var customerRepository = new InMemoryCustomerRepository();

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
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

        final var customerRepository = new InMemoryCustomerRepository();
        final var aCustomer = Customer.newCustomer(EXPECTED_NAME, EXPECTED_CPF, EXPECTED_EMAIL);
        customerRepository.create(aCustomer);
        final var createInput = new CreateCustomerUseCase.Input(EXPECTED_CPF, "john@email.com", EXPECTED_NAME);

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() {
        // given
        final var expectedError = "Customer already exists";

        final var customerRepository = new InMemoryCustomerRepository();
        final var aCustomer = Customer.newCustomer(EXPECTED_NAME, EXPECTED_CPF, EXPECTED_EMAIL);
        customerRepository.create(aCustomer);
        final var createInput = new CreateCustomerUseCase.Input("123.456.789-02", EXPECTED_EMAIL, EXPECTED_NAME);

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

}

