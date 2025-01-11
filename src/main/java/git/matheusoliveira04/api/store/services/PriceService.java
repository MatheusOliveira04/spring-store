package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.Price;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PriceService {

    List<Price> findAll(Pageable pageable);

    Price findById(UUID id);

    Price insert(Price price);

    Price update(Price price);

    void delete(UUID id);
}
