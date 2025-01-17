package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.Product;
import git.matheusoliveira04.api.store.models.dtos.ProductRequest;
import git.matheusoliveira04.api.store.services.PriceService;
import git.matheusoliveira04.api.store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody @Valid ProductRequest productRequest, UriComponentsBuilder uriComponentsBuilder) {
        Product product = new Product(productRequest, priceService.findById(productRequest.priceId()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri())
                .body(productService.insert(product));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody @Valid ProductRequest productRequest, @PathVariable UUID id) {
        Product product = new Product(productRequest, priceService.findById(productRequest.priceId()));
        product.setId(id);
        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
