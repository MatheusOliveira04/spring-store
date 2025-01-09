package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.ClientRequest;
import git.matheusoliveira04.api.store.models.dtos.ClientResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity(name = "tb_client")
public class Client extends Person{

    @Id @Setter
    @GeneratedValue
    private UUID id;

    @Size(min = 11, max = 11)
    private String cpf;

    public Client(ClientRequest clientRequest, Address addresses) {
        super(clientRequest.name(), clientRequest.telephone(), addresses, clientRequest.dateOfBirth(), clientRequest.email());
        this.id = clientRequest.id();
        this.cpf = clientRequest.cpf();
    }

    public ClientResponse toDtoResponse() {
        return new ClientResponse(this.getId(), this.getCpf(), this.getName(), this.getTelephone(), this.getAddress(), this.getDateOfBirth(), this.getEmail());
    }
}
