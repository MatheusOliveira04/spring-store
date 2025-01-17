package git.matheusoliveira04.api.store.models.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PriceRequest(
        @NotNull(message = "The field cannot be null")
        @DecimalMin(value = "0.0", message = "The field must contains a minimum value of 0.0")
        BigDecimal costPrice,
        @NotNull(message = "The field cannot be null")
        @DecimalMin(value = "0.1", message = "The field must contains a minimum value of 0.1")
        BigDecimal salePrice
) {

}
