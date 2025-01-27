package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.Client;
import git.matheusoliveira04.api.store.models.dtos.requests.ClientRequest;
import git.matheusoliveira04.api.store.models.dtos.responses.ClientResponse;
import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.ClientService;
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
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AddressService addressService;

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(clientService.findAll(pageable)
                .stream()
                .map(Client::toDtoResponse)
                .toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(clientService.findById(id).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<ClientResponse> insert(@RequestBody @Valid ClientRequest clientRequest, UriComponentsBuilder uriComponentsBuilder) {
        var client = new Client(clientRequest, addressService.findById(clientRequest.addressId()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/client/{id}").buildAndExpand(client.getId()).toUri())
                .body(clientService.insert(client).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@RequestBody @Valid ClientRequest clientRequest, @PathVariable UUID id) {
        var client = new Client(clientRequest, addressService.findById(clientRequest.addressId()));
        client.setId(id);
        return ResponseEntity.ok(clientService.update(client).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
