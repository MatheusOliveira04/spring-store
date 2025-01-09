package git.matheusoliveira04.api.store.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientRequest(
        UUID id,
        String cpf,
        @NotBlank(message = "The field cannot be empty")
        String name,
        List<String> telephone,
        @NotNull(message = "The field cannot be null")
        UUID addressId,
        LocalDate dateOfBirth,
        String email
) {
}
