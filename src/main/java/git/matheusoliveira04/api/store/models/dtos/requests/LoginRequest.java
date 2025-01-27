package git.matheusoliveira04.api.store.models.dtos.requests;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "The field cannot be empty")
        String email,
        @NotEmpty(message = "The field cannot be empty")
        String password
) {
}
