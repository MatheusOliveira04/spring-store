package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.Address;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    List<Address> findAll(Pageable pageable);

    Address findById(UUID id);

    Address insert(Address address);

    Address update(Address address);

    void delete(UUID id);
}
