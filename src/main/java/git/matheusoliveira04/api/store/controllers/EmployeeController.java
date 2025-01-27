package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.Employee;
import git.matheusoliveira04.api.store.models.dtos.requests.EmployeeRequest;
import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.EmployeeService;
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
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AddressService addressService;

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<Employee>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Employee> insert(@RequestBody @Valid EmployeeRequest employeeRequest, UriComponentsBuilder uriComponentsBuilder) {
        Employee employee = new Employee(employeeRequest, addressService.findById(employeeRequest.addressId()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri())
                .body(employeeService.insert(employee));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@RequestBody @Valid EmployeeRequest employeeRequest, @PathVariable UUID id) {
        Employee employee = new Employee(employeeRequest, addressService.findById(employeeRequest.addressId()));
        employee.setId(id);
        return ResponseEntity.ok(employeeService.update(employee));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
