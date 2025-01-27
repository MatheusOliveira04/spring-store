package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findAll(Pageable pageable);

    User findById(UUID id);

    User insert(User user);

    User update(User user);

    void delete (UUID id);

    Optional<User> findByEmail(String email);
}
