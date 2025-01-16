package git.matheusoliveira04.api.store.repositories;

import git.matheusoliveira04.api.store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByCodeBar(String codeBar);
}
