package git.matheusoliveira04.api.store.models.dtos;

import git.matheusoliveira04.api.store.models.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ClientRequest(
        UUID id,
        @NotEmpty(message = "The field cannot be empty")
        @Size(min = 11, max = 11, message = "The field must contains 11 characters")
        @Pattern(regexp = "\\d+", message = "The field must contain only numbers")
        String cpf,
        @NotEmpty(message = "The field cannot be empty")
        @NotNull(message = "The field cannot be null")
        String name,
        @NotNull(message = "The list cannot be null")
        @Size(min = 1, message = "The telephone list must contain at least one phone number.")
        List<@NotNull @Pattern(regexp = "\\d+", message = "The field must contain only numbers")
        @Size(min = 8, max = 13, message = "Request size telephone is minus than 8 or plus more 13") String> telephone,
        @NotNull(message = "The field cannot be null")
        @Valid AddressRequest address,
        LocalDate dateOfBirth,
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$", message = "The field must include @ and .com in the field")
        String email
) {
}
