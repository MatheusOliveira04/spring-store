package git.matheusoliveira04.api.store.models.dtos;

import git.matheusoliveira04.api.store.models.Client;
import git.matheusoliveira04.api.store.models.Employee;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SaleResponse(
        UUID id,
        String description,
        BigDecimal valueTotal,
        Integer quantityTotal,
        LocalDateTime dateTime,
        Employee employee,
        ClientResponse client
) {
}
