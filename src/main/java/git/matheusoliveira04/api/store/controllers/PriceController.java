package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.Price;
import git.matheusoliveira04.api.store.models.dtos.requests.PriceRequest;
import git.matheusoliveira04.api.store.services.PriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<Price>> findAll(Pageable pageable){
        return ResponseEntity.ok(priceService.findAll(pageable));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<Price> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(priceService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Price> insert(@RequestBody @Valid PriceRequest priceRequest, UriComponentsBuilder uriComponentsBuilder) {
        Price price = new Price(priceRequest);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/price/{id}").buildAndExpand(price.getId()).toUri())
                .body(priceService.insert(price));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Price> update(@RequestBody @Valid PriceRequest priceRequest, @PathVariable UUID id) {
        Price price = new Price(priceRequest);
        price.setId(id);
        return ResponseEntity.ok(priceService.update(price));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        priceService.delete(id);
        return ResponseEntity.noContent().build();
    }
 }

