package git.matheusoliveira04.api.store.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientRequest(
        UUID id,
        String cpf,
        @NotBlank(message = "The field cannot be empty")
        String name,
        @NotNull(message = "The list cannot be null")
        @Size(min = 1, message = "The telephone list must contain at least one phone number.")
        List<@NotNull @Size(min = 8, max = 13, message = "Request size telephone is minus than 8 or plus more 13") String> telephone,
        @NotNull(message = "The field cannot be null")
        UUID addressId,
        LocalDate dateOfBirth,
        String email
) {
}
