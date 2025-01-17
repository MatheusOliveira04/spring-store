package git.matheusoliveira04.api.store.models.dtos;

import git.matheusoliveira04.api.store.models.Price;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record ProductRequest(
        @NotEmpty(message = "The field cannot be empty")
        String name,
        @NotEmpty(message = "The field cannot be empty")
        @Pattern(regexp = "\\d+", message = "The field must contain only numbers")
        String codeBar,
        @NotNull(message = "The field cannot be null")
        @Min(value = 0, message = "The field must contains a minimum value of 0")
        Integer stock,
        @NotNull(message = "The field cannot be null")
        UUID priceId
) {
}
