package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.ItemSale;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ItemSaleService {

    List<ItemSale> findAll(Pageable pageable);

    ItemSale findById(UUID id);

    ItemSale insert(ItemSale itemSale);

    ItemSale update(ItemSale itemSale);

    void delete(UUID id);
}
