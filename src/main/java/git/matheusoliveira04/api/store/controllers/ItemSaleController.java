package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.ItemSale;
import git.matheusoliveira04.api.store.models.dtos.requests.ItemSaleRequest;
import git.matheusoliveira04.api.store.models.dtos.responses.ItemSaleResponse;
import git.matheusoliveira04.api.store.services.ItemSaleService;
import git.matheusoliveira04.api.store.services.ProductService;
import git.matheusoliveira04.api.store.services.SaleService;
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
@RequestMapping("/item-sale")
public class ItemSaleController {

    @Autowired
    private ItemSaleService itemSaleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<ItemSaleResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok(itemSaleService.findAll(pageable)
                .stream()
                .map(ItemSale::toDtoResponse)
                .toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<ItemSaleResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(itemSaleService.findById(id).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<ItemSaleResponse> insert(@RequestBody @Valid ItemSaleRequest itemSaleRequest, UriComponentsBuilder uriComponentsBuilder) {
        ItemSale itemSale = new ItemSale(itemSaleRequest, productService.findById(itemSaleRequest.productId()), saleService.findById(itemSaleRequest.saleId()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/item-sale/{id}").buildAndExpand(itemSale.getId()).toUri())
                .body(itemSaleService.insert(itemSale).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<ItemSaleResponse> update(@RequestBody @Valid ItemSaleRequest itemSaleRequest, @PathVariable UUID id) {
        ItemSale itemSale = new ItemSale(itemSaleRequest, productService.findById(itemSaleRequest.productId()), saleService.findById(itemSaleRequest.saleId()));
        itemSale.setId(id);
        return ResponseEntity.ok(itemSaleService.update(itemSale).toDtoResponse());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        itemSaleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
