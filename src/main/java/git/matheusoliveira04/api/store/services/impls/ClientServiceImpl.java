package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Client;
import git.matheusoliveira04.api.store.repositories.ClientRepository;
import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.ClientService;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private void validateCpfIsUnique(Client client) {
        Client clientFound = clientRepository.findByCpf(client.getCpf());
        if(clientFound != null && clientFound.getId() != client.getId()) {
            throw new IntegrityViolationException("A cpf already exists for another client. Each client must have a cpf email.");
        }
    }

    private void validateEmailIsUnique(Client client) {
        if (client.getEmail() != null && !client.getEmail().isEmpty()) {
            Client find = clientRepository.findByEmail(client.getEmail());
            if (find != null && find.getId() != client.getId()) {
                throw new IntegrityViolationException("An email already exists for another client. Each client must have an unique email.");
            }
        }
    }

    private void validateAddressIsUnique(Client client) {
        Client clientFound = clientRepository.findByAddress(client.getAddress());
        if (clientFound != null && clientFound.getId() != client.getId()) {
            throw new IntegrityViolationException("An address already exists for another client. Each client must have an address email.");
        }
    }

    private void validateClient(Client client) {
        validateCpfIsUnique(client);
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

    @Transactional
    @Override
    public Client insert(Client client) {
        validateClient(client);
        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public Client update(Client client) {
        findById(client.getId());
        validateClient(client);
        return clientRepository.save(client);
    }

    @Override
    public void delete(UUID id) {
        clientRepository.delete(findById(id));
    }
}
