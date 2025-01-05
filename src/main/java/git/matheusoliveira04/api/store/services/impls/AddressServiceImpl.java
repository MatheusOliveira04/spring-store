package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Address;
import git.matheusoliveira04.api.store.models.dtos.AddressViaCepResponse;
import git.matheusoliveira04.api.store.repositories.AddressRepository;
import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import git.matheusoliveira04.api.store.services.facade.ViaCepClientServiceFacade;
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
    private ViaCepClientServiceFacade viaCepClientServiceFacade;

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
        this.getAddressByCep(address);
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        this.getAddressByCep(address);
        this.findById(address.getId());
        return addressRepository.save(address);
    }

    @Override
    public void delete(UUID id) {
        addressRepository.delete(this.findById(id));
    }

    private void getAddressByCep(Address address) {
        if (isCepNotEmpty(address.getCep())) {
            AddressViaCepResponse addressViaCepResponse = fetchAddressByCep(address.getCep());
            if (addressViaCepResponse != null) {
               mapToAddress(addressViaCepResponse, address);
            }
        }
    }

    private void mapToAddress(AddressViaCepResponse addressViaCepResponse, Address address) {
        address.setUf(addressViaCepResponse.uf());
        //address.setCep(addressViaCepResponse.cep());
        address.setCity(addressViaCepResponse.localidade());
        address.setNeighborhood(addressViaCepResponse.bairro());
        address.setStreet(addressViaCepResponse.logradouro());
        address.setDescription(addressViaCepResponse.complemento());
        address.setNumber(address.getNumber());
    }

    private AddressViaCepResponse fetchAddressByCep(String cep) {
        return viaCepClientServiceFacade.getAddressByCep(cep);
    }

    private Boolean isCepNotEmpty(String cep) {
        return cep != null && !cep.isEmpty();
    }

}
