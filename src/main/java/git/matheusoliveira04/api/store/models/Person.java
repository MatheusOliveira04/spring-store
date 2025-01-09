package git.matheusoliveira04.api.store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Person {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false) @Size(min = 1)
    private List<String> telephone;

    @JoinColumn(name = "address_id", unique = true ,nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Past
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$")
    private String email;
}
