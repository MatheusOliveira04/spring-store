package git.matheusoliveira04.api.store.clients.dtosClients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressClientResponse {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String erro = "false";

    public AddressClientResponse(String cep, String logradouro, String complemento, String bairro, String localidade, String uf, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.estado = estado;
    }

    public AddressClientResponse(String erro) {
        this.erro = erro;
    }
}
