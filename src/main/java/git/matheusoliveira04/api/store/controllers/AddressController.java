package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.Address;
import git.matheusoliveira04.api.store.models.dtos.requests.AddressRequest;
import git.matheusoliveira04.api.store.models.dtos.responses.AddressResponse;
import git.matheusoliveira04.api.store.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAll(@PageableDefault(sort = "city") Pageable pageable) {
        return ResponseEntity.ok(addressService.findAll(pageable)
                .stream()
                .map(Address::toDtoResponse)
                .toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.findById(id).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<AddressResponse> insert(@RequestBody @Valid AddressRequest addressRequest, UriComponentsBuilder uriComponentsBuilder) {
        Address address = addressService.insert(new Address(addressRequest));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/address/{id}").buildAndExpand(address.getId()).toUri())
                .body(address.toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(@RequestBody @Valid AddressRequest addressRequest, @PathVariable UUID id) {
        Address address = new Address(addressRequest);
        address.setId(id);
        return ResponseEntity.ok(addressService.update(address).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
