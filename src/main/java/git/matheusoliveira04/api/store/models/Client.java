package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.ClientRequest;
import git.matheusoliveira04.api.store.models.dtos.ClientResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "tb_client")
public class Client extends Person{

    @Size(min = 11, max = 11)
    @Pattern(regexp = "\\d+")
    @Column(nullable = false)
    private String cpf;

    public Client(UUID id, String name, List<String> telephone, Address address, LocalDate dateOfBirth, String email, String cpf) {
        super(id, name, telephone, address, dateOfBirth, email);
        this.cpf = cpf;
    }

    public Client(ClientRequest clientRequest, Address address) {
        this(null, clientRequest.name(), clientRequest.telephone(),
                address, clientRequest.dateOfBirth(), clientRequest.email(), clientRequest.cpf());
    }

    public ClientResponse toDtoResponse() {
        return new ClientResponse(this.getId(), this.getCpf(), this.getName(), this.getTelephone(), this.getAddress(), this.getDateOfBirth(), this.getEmail());
    }
}
