package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.AddressRequest;
import git.matheusoliveira04.api.store.models.dtos.AddressResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false) @Size(min = 2, max = 2)
    private String uf;
    @Pattern(regexp = "\\d+")
    @Size(min = 8, max = 8)
    private String cep;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String description;
    @Pattern(regexp = "\\d+")
    private String number;

    public Address(AddressRequest addressRequest) {
        this.id = addressRequest.id();
        this.uf = addressRequest.uf();
        this.cep = addressRequest.cep();
        this.city = addressRequest.city();
        this.neighborhood = addressRequest.neighborhood();
        this.street = addressRequest.street();
        this.description = addressRequest.description();
        this.number = addressRequest.number();
    }

    public AddressResponse toDtoResponse() {
        return new AddressResponse(id, uf, cep, city, neighborhood, street, description, number);
    }

}
