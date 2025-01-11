package git.matheusoliveira04.api.store.services.validations.rules;

import git.matheusoliveira04.api.store.models.Person;
import git.matheusoliveira04.api.store.repositories.ClientRepository;
import git.matheusoliveira04.api.store.services.validations.ValidationRule;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;

public class ClientEmailUniqueValidation implements ValidationRule<Person> {

    private final ClientRepository clientRepository;

    public ClientEmailUniqueValidation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void validate(Person person) {
        if (clientRepository.findByEmail(person.getEmail()) != null) {
            throw new IntegrityViolationException("An email already exists for another client. Each client must have a unique email.");
        }
    }
}
