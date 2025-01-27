package git.matheusoliveira04.api.store.models.dtos.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemSaleRequest(
        @NotNull(message = "The field cannot be null")
        @Min(value = 0, message = "The field must contains a minimum amount of 0")
        Integer quantity,
        @NotNull(message = "The field cannot be null")
        @DecimalMin(value = "0.1", message = "The field must contains a minimum amount of 0")
        BigDecimal amount,
        @NotNull(message = "The field cannot be null")
        UUID productId,
        @NotNull(message = "The field cannot be null")
        UUID saleId
) {
}
