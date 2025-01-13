package git.matheusoliveira04.api.store.clients;

import git.matheusoliveira04.api.store.clients.dtosClients.AddressClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ViaCep", url = "viacep.com.br/ws")
public interface CepClient {

    @GetMapping("/{cep}/json/")
    ResponseEntity<AddressClientResponse> validateCep(@PathVariable String cep);
}
