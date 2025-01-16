package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Address;
import git.matheusoliveira04.api.store.repositories.AddressRepository;
import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> findAll(Pageable pageable) {
        List<Address> addressList = addressRepository.findAll(pageable).toList();
        if (addressList.isEmpty()) {
            throw new ObjectNotFoundException("No address found!");
        }
        return addressList;
    }

    @Override
    public Address findById(UUID id) {
        return addressRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Address not found with id: " + id));
    }

    @Override
    public Address insert(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        findById(address.getId());
        return addressRepository.save(address);
    }

    @Override
    public void delete(UUID id) {
        addressRepository.delete(findById(id));
    }

}
