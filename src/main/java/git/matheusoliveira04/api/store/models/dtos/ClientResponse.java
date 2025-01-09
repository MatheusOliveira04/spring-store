package git.matheusoliveira04.api.store.models.dtos;

import git.matheusoliveira04.api.store.models.Address;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientResponse(
        UUID id,
        String cpf,
        String name,
        List<String> telephone,
        Address address,
        LocalDate dateOfBirth,
        String email
        ) {
}
