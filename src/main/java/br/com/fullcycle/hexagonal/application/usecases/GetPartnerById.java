package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.services.PartnerService;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

public class GetPartnerById extends UseCase<GetPartnerById.Input, Optional<GetPartnerById.Output>> {
    private final PartnerService partnerService;

    public GetPartnerById(final PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return partnerService.findById(input.id)
                .map(p -> new Output(p.getId(), p.getCnpj(), p.getName(), p.getEmail()));
    }

    public record Input(Long id) {
    }

    public record Output(Long id, String cnpj, String name, String email) {
    }
}
