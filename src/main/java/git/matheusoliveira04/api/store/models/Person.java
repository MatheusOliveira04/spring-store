package git.matheusoliveira04.api.store.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class Person {

    @Id @Setter
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Size(min = 1)
    @Column(nullable = false)
    private List<@NotNull @Size(min = 8, max = 13) @Pattern(regexp = "\\d+") String> telephone;

    @JoinColumn(name = "address_id", unique = true ,nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Past
    private LocalDate dateOfBirth;

    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com$")
    private String email;
}
