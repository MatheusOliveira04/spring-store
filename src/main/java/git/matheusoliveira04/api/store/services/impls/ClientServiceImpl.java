package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Client;
import git.matheusoliveira04.api.store.repositories.AddressRepository;
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

    @Autowired
    private AddressService addressService;

    private void validateEmailIsUnique(Client client) {
        if (client.getEmail() != null && !client.getEmail().isEmpty()) {
            Client find = clientRepository.findByEmail(client.getEmail()).get();
            if (find != null && find.getId() != client.getId()) {
                throw new IntegrityViolationException("An email already exists for another client. Each client must have a unique email.");
            }
        }
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
        validateEmailIsUnique(client);
        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public Client update(Client client) {
        validateEmailIsUnique(client);
        Client clientFound = findById(client.getId());
        updateAddress(client, clientFound);
        return clientRepository.save(client);
    }

    @Override
    public void delete(UUID id) {
        clientRepository.delete(findById(id));
    }

    private void updateAddress(Client client, Client clientFound) {
        client.getAddress().setId(clientRepository.findByAddress(clientFound.getAddress()).get().getAddress().getId());
    }
}
