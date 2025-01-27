package git.matheusoliveira04.api.store.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record UserRequest(
        @NotEmpty
        String name,
        @NotEmpty
        String email,
        @NotEmpty
        String password,
        @NotEmpty
        String roles
) {
}
