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

    private void addressIsUnique(Client client) {
        if (clientRepository.findByAddress(client.getAddress()) != null) {
            throw new IntegrityViolationException("An address already exists for another client. Each client must have a unique address.");
        }
    }

    private void validateTelephoneCharactersLength(Client client) {
        var isNotValid = client.getTelephone()
                .stream()
                .anyMatch(telephone -> telephone.length() < 8 || telephone.length() > 13);
        if (isNotValid) {
            throw new IntegrityViolationException("One or more phone numbers have an invalid length. Phone numbers must be between 8 and 13 characters long.");
        }
    }

    private void validateTelephoneListIsNotEmpty(Client client) {
        if(client.getTelephone() == null || client.getTelephone().isEmpty()) {
            throw new IntegrityViolationException("The telephone cannot be empty or null");
        }
    }

    private void validateTelephone(Client client) {
        this.validateTelephoneListIsNotEmpty(client);
        this.validateTelephoneCharactersLength(client);
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

    @Override
    public Client insert(Client client) {
        this.validateTelephone(client);
        this.addressIsUnique(client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        this.validateTelephone(client);
        this.addressIsUnique(client);
        this.findById(client.getId());
        return clientRepository.save(client);
    }

    @Override
    public void delete(UUID id) {
        clientRepository.delete(this.findById(id));
    }
}
