package git.matheusoliveira04.api.store.services.impls;

import git.matheusoliveira04.api.store.models.Employee;
import git.matheusoliveira04.api.store.repositories.EmployeeRepository;
import git.matheusoliveira04.api.store.services.EmployeeService;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private void validateEmailIsUnique(Employee employee) {
        if (employee.getEmail() != null && !employee.getEmail().isEmpty()) {
            Employee employeeFound = employeeRepository.findByEmail(employee.getEmail());
            if (employeeFound != null && employeeFound.getId() != employee.getId()) {
                throw new IntegrityViolationException("An email already exists for another employee. Each employee must have an unique email.");
            }
        }
    }

    private void validateAddressIsUnique(Employee employee) {
        Employee employeeFound = employeeRepository.findByAddress(employee.getAddress());
        if (employeeFound != null && employeeFound.getId() != employee.getId()) {
            throw new IntegrityViolationException("An address already exists for another employee. Each employee must have an address email.");
        }
    }

    private void validates(Employee employee) {
        validateAddressIsUnique(employee);
        validateEmailIsUnique(employee);
    }

    @Override
    public List<Employee> findAll(Pageable pageable) {
        List<Employee> employeeList = employeeRepository.findAll(pageable).toList();
        if (employeeList.isEmpty()) {
            throw new ObjectNotFoundException("No employee found");
        }
        return employeeList;
    }

    @Override
    public Employee findById(UUID id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() ->  new ObjectNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee insert(Employee employee) {
        validates(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        findById(employee.getId());
        validates(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(UUID id) {
        employeeRepository.delete(findById(id));
    }
}
