package git.matheusoliveira04.api.store.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import git.matheusoliveira04.api.store.BaseTests;
import git.matheusoliveira04.api.store.models.Address;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.util.List;
import java.util.UUID;

@Transactional
public class AddressServiceImplTest extends BaseTests {

    @Autowired
    AddressService addressService;

    @Test
    @DisplayName("Test find existing id success")
    @Sql({"classpath:/sqls/address.sql"})
    void findByIdTest() {
        Address address = addressService.findById(UUID.fromString("f9a9e0f7-0c8b-42d3-bb65-599d8e2f0a29"));
        assertNotNull(address);
        assertEquals("f9a9e0f7-0c8b-42d3-bb65-599d8e2f0a29", address.getId().toString());
        assertEquals("SC", address.getUf());
        assertEquals("12345679", address.getCep());
        assertEquals("Tubarão", address.getCity());
        assertEquals("Central", address.getNeighborhood());
        assertEquals("Rua B", address.getStreet());
        assertEquals("Address teste 2", address.getDescription());
        assertEquals("121", address.getNumber());
    }

    @Test
    @DisplayName("Test insert new address success")
    void insertTest() {
        Address addressInserted = addressService
                .insert(new Address(null, "SP", "12345679", "Tubarão", "Central",
                        "Rua B", "Address teste 2", "121"));
        assertNotNull(addressInserted);
        assertNotNull(addressInserted.getId());
        assertEquals("SP", addressInserted.getUf());
        assertEquals("12345679", addressInserted.getCep());
        assertEquals("Tubarão", addressInserted.getCity());
        assertEquals("Central", addressInserted.getNeighborhood());
        assertEquals("Rua B", addressInserted.getStreet());
        assertEquals("Address teste 2", addressInserted.getDescription());
        assertEquals("121", addressInserted.getNumber());
        assertNotNull(addressService.findById(addressInserted.getId()));
    }

    @Test
    @DisplayName("Test update address success")
    @Sql({"classpath:/sqls/address.sql"})
    void updateTest() {
        Address addressFound = addressService.findById(UUID.fromString("f9a9e0f7-0c8b-42d3-bb65-599d8e2f0a29"));
        assertNotNull(addressFound);
        Address addressUpdate = addressService.update(new Address(addressFound.getId(), "RS", "9998887776", "City test", "Central update",
                "Street C", "Address test 2 update", "222"));
        assertNotNull(addressUpdate);
        addressFound = addressService.findById(UUID.fromString("f9a9e0f7-0c8b-42d3-bb65-599d8e2f0a29"));
        assertEquals("f9a9e0f7-0c8b-42d3-bb65-599d8e2f0a29", addressFound.getId().toString());
        assertEquals("RS", addressFound.getUf());
        assertEquals("9998887776", addressFound.getCep());
        assertEquals("City test", addressFound.getCity());
        assertEquals("Central update", addressFound.getNeighborhood());
        assertEquals("Street C", addressFound.getStreet());
        assertEquals("Address test 2 update", addressFound.getDescription());
        assertEquals("222", addressFound.getNumber());
    }

    @Test
    @DisplayName("Test delete address success")
    @Sql({"classpath:/sqls/address.sql"})
    void deleteTest() {
        List<Address> findAll = addressService.findAll(PageRequest.ofSize(Integer.MAX_VALUE));
        assertEquals(2, findAll.size());
        Address addressFound = addressService.findById(UUID.fromString("f9a9e0f7-0c8b-42d3-bb65-599d8e2f0a29"));
        assertNotNull(addressFound);
        addressService.delete(addressFound.getId());
        findAll = addressService.findAll(PageRequest.ofSize(Integer.MAX_VALUE));
        assertEquals(1, findAll.size());
    }

    @Test
    @DisplayName("Test find all existing address success")
    @Sql({"classpath:/sqls/address.sql"})
    void findAllTest() {
        List<Address> findAll = addressService.findAll(PageRequest.ofSize(Integer.MAX_VALUE));
        assertNotNull(findAll);
        assertFalse(findAll.isEmpty());
        assertEquals(2, findAll.size());
    }

    @Test
    @DisplayName("Test find existing id error, not found address with id")
    void findByIdTestErrorNotFound() {
        var exception = assertThrows(ObjectNotFoundException.class,
                () -> addressService.findById(UUID.fromString("00000000-0000-0000-0000-000000000000")));
        assertEquals("Address not found with id: 00000000-0000-0000-0000-000000000000", exception.getMessage());

    }

    @Test
    @DisplayName("Test find all existing address error, no address found")
    void findAllTestErrorNotFound() {
        var exception = assertThrows(ObjectNotFoundException.class,
                () -> addressService.findAll(PageRequest.ofSize(Integer.MAX_VALUE)));
        assertEquals("No address found!", exception.getMessage());
    }
}
