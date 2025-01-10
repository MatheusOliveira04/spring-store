package git.matheusoliveira04.api.store.services.validations.rules;

import git.matheusoliveira04.api.store.models.Person;
import git.matheusoliveira04.api.store.repositories.ClientRepository;
import git.matheusoliveira04.api.store.services.ValidationRule;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressUniqueValidation implements ValidationRule<Person> {

    private final ClientRepository clientRepository;

    public AddressUniqueValidation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void validate(Person person) {
            if(clientRepository.findByAddress(person.getAddress()) != null) {
                throw new IntegrityViolationException("An address already exists for another client. Each client must have a unique address.");
            }
    }
}
