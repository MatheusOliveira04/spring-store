package git.matheusoliveira04.api.store;

import git.matheusoliveira04.api.store.services.AddressService;
import git.matheusoliveira04.api.store.services.impls.AddressServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;


@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

    @Bean
    public AddressService addressService() {
        return new AddressServiceImpl();
    }
}
