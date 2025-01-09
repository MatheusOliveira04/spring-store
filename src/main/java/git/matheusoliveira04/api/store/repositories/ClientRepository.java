package git.matheusoliveira04.api.store.repositories;

import git.matheusoliveira04.api.store.models.Address;
import git.matheusoliveira04.api.store.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findByAddress(Address address);

}
