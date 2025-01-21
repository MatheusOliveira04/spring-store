package git.matheusoliveira04.api.store.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SaleRequest(
        @NotEmpty(message = "The field cannot be empty")
        String description,
        @NotNull(message = "The field cannot be null")
        UUID employeeId,
        @NotNull(message = "The field cannot be null")
        UUID clientId
) {
}
