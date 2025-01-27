package git.matheusoliveira04.api.store.models.dtos.requests;

import git.matheusoliveira04.api.store.models.enums.Position;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record EmployeeRequest(
        @NotNull(message = "The field cannot be null")
        Position position,
        @NotEmpty(message = "The field cannot be empty")
        @NotNull(message = "The field cannot be null")
        String name,
        @NotNull(message = "The list cannot be null")
        @Size(min = 1, message = "The telephone list must contain at least one phone number.")
        List<
                @NotNull
                @Pattern(regexp = "\\d+", message = "The field must contain only numbers")
                @Size(min = 8, max = 13, message = "Request size telephone is minus than 8 or plus more 13")
                        String> telephone,
        @NotNull(message = "The field cannot be null")
        UUID addressId,
        @Past(message = "The date must be earlier than today's date")
        LocalDate dateOfBirth,
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$", message = "The field must include @ and .com in the field")
        String email
) {
}
