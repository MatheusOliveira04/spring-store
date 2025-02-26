package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.requests.EmployeeRequest;
import git.matheusoliveira04.api.store.models.enums.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity(name = "tb_employee")
public class Employee extends Person {

    @Column(nullable = false)
    private Position position;

    public Employee(UUID id, String name, List<String> telephone, Address address, LocalDate dateOfBirth, String email, Position position) {
        super(id, name, telephone, address, dateOfBirth, email);
        this.position = position;
    }

    public Employee(EmployeeRequest employeeRequest, Address address) {
       this(null, employeeRequest.name(), employeeRequest.telephone(),
                address, employeeRequest.dateOfBirth(), employeeRequest.email(), employeeRequest.position());
    }
}
