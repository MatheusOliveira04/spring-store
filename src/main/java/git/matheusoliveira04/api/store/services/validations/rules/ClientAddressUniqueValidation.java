package git.matheusoliveira04.api.store.services.validations.rules;

import git.matheusoliveira04.api.store.models.Person;
import git.matheusoliveira04.api.store.repositories.ClientRepository;
import git.matheusoliveira04.api.store.services.validations.ValidationRule;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ClientAddressUniqueValidation implements ValidationRule<Person> {

    private final ClientRepository clientRepository;

    public ClientAddressUniqueValidation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void validate(Person person) {
            if(clientRepository.findByAddress(person.getAddress()) != null) {
                throw new IntegrityViolationException("An address already exists for another client. Each client must have a unique address.");
            }
    }
}
