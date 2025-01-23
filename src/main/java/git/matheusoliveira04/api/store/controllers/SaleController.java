package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.Sale;
import git.matheusoliveira04.api.store.models.dtos.SaleRequest;
import git.matheusoliveira04.api.store.models.dtos.SaleResponse;
import git.matheusoliveira04.api.store.services.ClientService;
import git.matheusoliveira04.api.store.services.EmployeeService;
import git.matheusoliveira04.api.store.services.SaleService;
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
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<SaleResponse>> findAll(@PageableDefault(sort = "code") Pageable pageable) {
        return ResponseEntity.ok(saleService.findAll(pageable)
                .stream()
                .map(Sale::toDtoResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(saleService.findById(id).toDtoResponse());
    }

    @PostMapping
    public ResponseEntity<SaleResponse> insert(@RequestBody @Valid SaleRequest saleRequest, UriComponentsBuilder uriComponentsBuilder) {
        Sale sale = new Sale(saleRequest, employeeService.findById(saleRequest.employeeId()), clientService.findById(saleRequest.clientId()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/sale/{id}").buildAndExpand(sale.getId()).toUri())
                .body(saleService.insert(sale).toDtoResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponse> update(@RequestBody @Valid SaleRequest saleRequest, @PathVariable UUID id) {
        Sale sale = new Sale(saleRequest, employeeService.findById(saleRequest.employeeId()), clientService.findById(saleRequest.clientId()));
        sale.setId(id);
        return ResponseEntity.ok(saleService.update(sale).toDtoResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
