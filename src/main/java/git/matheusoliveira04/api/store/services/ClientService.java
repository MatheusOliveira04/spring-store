package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.Client;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<Client> findAll(Pageable pageable);

    Client findById(UUID id);

    Client insert(Client client);

    Client update(Client client);

    void delete(UUID id);
}
