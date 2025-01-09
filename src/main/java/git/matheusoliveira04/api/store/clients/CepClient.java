package git.matheusoliveira04.api.store.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ApiConsultaCEP", url = "https://h-apigateway.conectagov.estaleiro.serpro.gov.br/api-cep/v1/consulta/cep")
public interface CepClient {

    @GetMapping("/{cep}")
    ResponseEntity<Void> validateCep(@PathVariable String cep);
}
