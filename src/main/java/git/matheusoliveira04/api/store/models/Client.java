package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.ClientRequest;
import git.matheusoliveira04.api.store.models.dtos.ClientResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "tb_client")
public class Client extends Person{

    @Pattern(regexp = "\\d+")
    @NotEmpty
    @Size(min = 11, max = 11)
    @Pattern(regexp = "\\d+")
    private String cpf;

    public Client(ClientRequest clientRequest) {
        super(clientRequest.id(), clientRequest.name(), clientRequest.telephone(), new Address(clientRequest.address()), clientRequest.dateOfBirth(), clientRequest.email());
        this.cpf = clientRequest.cpf();
    }

    public ClientResponse toDtoResponse() {
        return new ClientResponse(this.getId(), this.getCpf(), this.getName(), this.getTelephone(), this.getAddress(), this.getDateOfBirth(), this.getEmail());
    }
}
