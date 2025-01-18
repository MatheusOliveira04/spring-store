package git.matheusoliveira04.api.store.repositories;

import git.matheusoliveira04.api.store.models.Address;
import git.matheusoliveira04.api.store.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Employee findByEmail(String email);

    Employee findByAddress(Address address);
}
