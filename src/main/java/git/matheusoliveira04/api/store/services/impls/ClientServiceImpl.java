package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Client;
import git.matheusoliveira04.api.store.models.Person;
import git.matheusoliveira04.api.store.repositories.ClientRepository;
import git.matheusoliveira04.api.store.services.ClientService;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import git.matheusoliveira04.api.store.services.validations.rules.ClientAddressUniqueValidation;
import git.matheusoliveira04.api.store.services.validations.rules.ClientEmailUniqueValidation;
import git.matheusoliveira04.api.store.services.validations.ValidationChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private void validateAttributes(Client client) {
        ValidationChain<Person> validationChain = new ValidationChain<>(
                List.of(
                        new ClientAddressUniqueValidation(clientRepository),
                        new ClientEmailUniqueValidation(clientRepository)));
        validationChain.execute(client);
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
        this.validateAttributes(client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        this.validateAttributes(client);
        this.findById(client.getId());
        return clientRepository.save(client);
    }

    @Override
    public void delete(UUID id) {
        clientRepository.delete(this.findById(id));
    }
}
