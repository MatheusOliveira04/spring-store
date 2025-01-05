package git.matheusoliveira04.api.store.models.dtos;

public record AddressViaCepResponse(
        String cep,
        String uf,
        String localidade,
        String bairro,
        String logradouro,
        String complemento
) {
}
