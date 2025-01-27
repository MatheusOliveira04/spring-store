package git.matheusoliveira04.api.store.models.dtos.responses;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        String uf,
        String cep,
        String city,
        String neighborhood,
        String street,
        String description,
        String number
) {
}
