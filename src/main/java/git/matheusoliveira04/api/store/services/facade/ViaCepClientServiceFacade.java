package git.matheusoliveira04.api.store.services.facade;

import git.matheusoliveira04.api.store.models.dtos.AddressViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ViaCep", url = "viacep.com.br/ws/")
public interface ViaCepClientServiceFacade {

    @GetMapping("/{cep}/json/") //01001000
    AddressViaCepResponse getAddressByCep(@PathVariable String cep);
}
