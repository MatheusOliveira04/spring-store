package git.matheusoliveira04.api.store.services.fetchers.impls;

import git.matheusoliveira04.api.store.clients.CepClient;
import git.matheusoliveira04.api.store.services.excepitions.ApiClientException;
import git.matheusoliveira04.api.store.services.fetchers.CepServiceFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CepServiceFetcherImpl implements CepServiceFetcher {

    private static final Logger logger = LoggerFactory.getLogger(CepServiceFetcherImpl.class);

    @Autowired
    private CepClient cepClient;

    @Override
    public void validateCep(String cep) {
        try {
            var response = cepClient.validateCep(cep);

            if (response.getStatusCode().is4xxClientError()) {
                throw new ApiClientException("ZIP code: " + cep + " not found");
            }

        } catch (Exception e) {
            throw new ApiClientException("Error while finding ZIP code.");
        }
    }
}
