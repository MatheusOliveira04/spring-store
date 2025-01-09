package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Address;
import git.matheusoliveira04.api.store.repositories.AddressRepository;
import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import git.matheusoliveira04.api.store.services.fetchers.CepServiceFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CepServiceFetcher cepServiceFetcher;

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
        this.findById(address.getId());
        return addressRepository.save(address);
    }

    @Override
    public void delete(UUID id) {
        addressRepository.delete(this.findById(id));
    }

    private void findCepValid(Address address) {
        if (isCepNotEmpty(address.getCep())) {
            cepServiceFetcher.validateCep(address.getCep());
        }
    }

    private Boolean isCepNotEmpty(String cep) {
        return cep != null && !cep.isEmpty();
    }

}
