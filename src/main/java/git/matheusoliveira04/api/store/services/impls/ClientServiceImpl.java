package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Client;
import git.matheusoliveira04.api.store.repositories.ClientRepository;
import git.matheusoliveira04.api.store.services.ClientService;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private void validateEmailIsUnique(Client client) {
        if (client.getEmail() != null && clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new IntegrityViolationException("An email already exists for another client. Each client must have a unique email.");
        }
    }

    private void validateAddressIsUnique(Client client) {
        if (client.getAddress() != null && clientRepository.findByAddress(client.getAddress()).isPresent() ) {
            throw new IntegrityViolationException("An address already exists for another client. Each client must have a unique address.");
        }
    }

    private void validateAttributes(Client client) {
        validateAddressIsUnique(client);
        validateEmailIsUnique(client);
    }

    @Override
    public List<Client> findAll(Pageable pageable) {
        List<Client> clientList = clientRepository.findAll(pageable).toList();
        if (clientList.isEmpty()) {
            throw new ObjectNotFoundException("No client found!");
        }
        return clientList;
    }

    @Override
    public Client findById(UUID id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Client not found with id: " + id));
    }

    @org.springframework.transaction.annotation.Transactional
    @Override
    public Client insert(Client client) {
        validateAttributes(client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        validateAttributes(client);
        findById(client.getId());
        return clientRepository.save(client);
    }

    @Override
    public void delete(UUID id) {
        clientRepository.delete(findById(id));
    }
}
