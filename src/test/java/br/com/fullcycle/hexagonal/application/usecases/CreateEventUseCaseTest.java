package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.InMemoryEventRepository;
import br.com.fullcycle.hexagonal.application.InMemoryPartnerRepository;
import br.com.fullcycle.hexagonal.application.domain.Partner;
import br.com.fullcycle.hexagonal.application.domain.PartnerId;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


class CreateEventUseCaseTest {
    static final String EXPECTED_DATE = "2021-01-01";
    static final String EXPECTED_NAME = "Disney on Ice";
    static final int EXPECTED_TOTAL_SPOTS = 100;

    CreateEventUseCase useCase;
    PartnerRepository partnerRepository;

    @BeforeEach
    void setUp() {
        partnerRepository = new InMemoryPartnerRepository();

        useCase = new CreateEventUseCase(new InMemoryEventRepository(), partnerRepository);
    }


    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() {
        // given
        final var partner = Partner.newPartner("Disney", "12.345.678/0001-00", "john.doe@gmail.com");
        final var expectedPartnerId = partner.partnerId().value();

        partnerRepository.create(partner);

        final var createInput = new CreateEventUseCase.Input(EXPECTED_DATE, EXPECTED_NAME, expectedPartnerId, EXPECTED_TOTAL_SPOTS);

        //when
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(EXPECTED_DATE, output.date());
        Assertions.assertEquals(EXPECTED_NAME, output.name());
        Assertions.assertEquals(EXPECTED_TOTAL_SPOTS, output.totalSpots());
        Assertions.assertEquals(expectedPartnerId, output.partnerId());
    }

    @Test
    @DisplayName("Não deve criar um evento quando partner não for encontrado")
    public void testCreateEvent_shouldNotCreateEventWhenPartnerIsNotFound() throws Exception {
        // given
        final var expectedPartnerId = PartnerId.unique().value();
        final var expectedError = "Partner not found";

        final var createInput = new CreateEventUseCase.Input(EXPECTED_DATE, EXPECTED_NAME, expectedPartnerId, EXPECTED_TOTAL_SPOTS);

        //when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}