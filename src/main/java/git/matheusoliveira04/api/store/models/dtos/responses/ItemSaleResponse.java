package git.matheusoliveira04.api.store.models.dtos.responses;

import git.matheusoliveira04.api.store.models.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemSaleResponse(
        UUID id,
        Integer quantity,
        BigDecimal value,
        Product product,
        SaleResponse sale
) {
}
