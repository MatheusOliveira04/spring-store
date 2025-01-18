package git.matheusoliveira04.api.store.services;

import git.matheusoliveira04.api.store.models.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> findAll(Pageable pageable);

    Employee findById(UUID id);

    Employee insert(Employee employee);

    Employee update(Employee employee);

    void delete(UUID id);
}
