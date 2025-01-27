package git.matheusoliveira04.api.store.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ClientServiceTest {

    @Autowired
    AddressService addressService;

    @Autowired
    ClientService clientService;

    @Test
    @DisplayName("")
    @Sql({"classpath:/sqls/address.sql"})
    @Sql({"classpath:/sqls/client.sql"})
    void test() {

    }
}
