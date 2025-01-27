package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.User;
import git.matheusoliveira04.api.store.repositories.UserRepository;
import git.matheusoliveira04.api.store.services.UserService;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll(Pageable pageable) {
        List<User> findAll = userRepository.findAll(pageable).toList();
        if (findAll.isEmpty()) {
            throw new ObjectNotFoundException("No user found.");
        }
        return findAll;
    }

    @Override
    public User findById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with id: " + id));
    }

    @Override
    public User insert(User user) {
        verifyIfExistEmail(user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        findById(user.getId());
        verifyIfExistEmail(user);
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(findById(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> userFindByEmail = userRepository.findByEmail(email);
        if (userFindByEmail.isPresent()) {
            return userFindByEmail;
        }
        throw new ObjectNotFoundException("No email: %s found for the user.".formatted(email));
    }

    private void verifyIfExistEmail(User user) {
        Optional<User> userFindByEmail = userRepository.findByEmail(user.getEmail());
        if (userFindByEmail.isPresent() && userFindByEmail.get().getId() != user.getId()) {
            throw new IntegrityViolationException("Email: %s already exists".formatted(user.getEmail()));
        }
    }
}
