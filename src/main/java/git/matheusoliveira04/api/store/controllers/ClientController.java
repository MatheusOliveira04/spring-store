package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.Client;
import git.matheusoliveira04.api.store.models.dtos.ClientRequest;
import git.matheusoliveira04.api.store.models.dtos.ClientResponse;
import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(clientService.findAll(pageable)
                .stream()
                .map(Client::toDtoResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(clientService.findById(id).toDtoResponse());
    }

    @PostMapping
    public ResponseEntity<ClientResponse> insert(@RequestBody @Valid ClientRequest clientRequest, UriComponentsBuilder uriComponentsBuilder) {
        var client = new Client(clientRequest, addressService.findById(clientRequest.addressId()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/client/{id}").buildAndExpand(client.getId()).toUri())
                .body(clientService.insert(client).toDtoResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@RequestBody @Valid ClientRequest clientRequest, @PathVariable UUID id) {
        var client = new Client(clientRequest, addressService.findById(clientRequest.addressId()));
        client.setId(id);
        return ResponseEntity.ok(clientService.update(client).toDtoResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
