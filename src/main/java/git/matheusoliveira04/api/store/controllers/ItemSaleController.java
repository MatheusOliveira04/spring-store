package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.ItemSale;
import git.matheusoliveira04.api.store.models.dtos.ItemSaleRequest;
import git.matheusoliveira04.api.store.services.ItemSaleService;
import git.matheusoliveira04.api.store.services.ProductService;
import git.matheusoliveira04.api.store.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<ItemSale>> findAll(Pageable pageable) {
        return ResponseEntity.ok(itemSaleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemSale> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(itemSaleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemSale> insert(@RequestBody @Valid ItemSaleRequest itemSaleRequest, UriComponentsBuilder uriComponentsBuilder) {
        ItemSale itemSale = new ItemSale(itemSaleRequest, productService.findById(itemSaleRequest.productId()), saleService.findById(itemSaleRequest.saleId()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/item-sale/{id}").buildAndExpand(itemSale.getId()).toUri())
                .body(itemSaleService.insert(itemSale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemSale> update(@RequestBody @Valid ItemSaleRequest itemSaleRequest, @PathVariable UUID id) {
        ItemSale itemSale = new ItemSale(itemSaleRequest, productService.findById(itemSaleRequest.productId()), saleService.findById(itemSaleRequest.saleId()));
        itemSale.setId(id);
        return ResponseEntity.ok(itemSaleService.update(itemSale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        itemSaleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
