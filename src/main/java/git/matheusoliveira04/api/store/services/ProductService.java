package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> findAll(Pageable pageable);

    Product findById(UUID id);

    Product insert(Product product);

    Product update(Product product);

    void delete(UUID id);

    void subtractStock(Product product, Integer quantity);

    void addStock(Product product, Integer quantity);
}
