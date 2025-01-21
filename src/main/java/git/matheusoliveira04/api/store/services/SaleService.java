package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.Sale;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface SaleService {

    List<Sale> findAll(Pageable pageable);

    Sale findById(UUID id);

    Sale insert(Sale sale);

    Sale update(Sale sale);

    void delete(UUID id);

    Sale addTotalValue(Sale sale, BigDecimal addValue, Integer quantityItemSale);

    Sale subtractTotalValue(Sale sale, BigDecimal subtractValue, Integer quantityItemSale);

    Sale addTotalQuantity(Sale sale, Integer addQuantity);

    Sale subtractTotalQuantity(Sale sale, Integer subtractQuantity);
}
