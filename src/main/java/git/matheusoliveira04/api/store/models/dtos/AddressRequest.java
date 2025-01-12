package git.matheusoliveira04.api.store.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AddressRequest(
        UUID id,
        @NotBlank(message = "The field cannot be empty") @Size(min = 2, max = 2, message = "The field size must be between "+ 2 +" and " + 2)
        String uf,
        @Size(min = 8, max = 8, message = "The field size must be between "+ 8 +" and " + 8)
        String cep,
        @NotBlank(message = "The field cannot be empty")
        String city,
        @NotBlank(message = "The field cannot be empty")
        String neighborhood,
        @NotBlank(message = "The field cannot be empty")
        String street,
        @NotBlank(message = "The field cannot be empty")
        String description,
        String number
) {
}
