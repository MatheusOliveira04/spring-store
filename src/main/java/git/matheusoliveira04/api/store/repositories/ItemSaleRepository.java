package git.matheusoliveira04.api.store.repositories;

import git.matheusoliveira04.api.store.models.ItemSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemSaleRepository extends JpaRepository<ItemSale, UUID> {
}
