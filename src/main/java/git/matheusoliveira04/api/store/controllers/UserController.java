package git.matheusoliveira04.api.store.controllers;

import git.matheusoliveira04.api.store.models.User;
import git.matheusoliveira04.api.store.models.UserRequest;
import git.matheusoliveira04.api.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<User>> findAll(@PageableDefault(sort = "name") Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email).get());
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<User> insert(@RequestBody @Valid UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {
        User user = userService.insert(new User(userRequest));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri())
                .body(user);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody @Valid UserRequest userRequest, @PathVariable UUID id) {
        User user = new User(userRequest);
        user.setId(id);
        return ResponseEntity.ok(userService.update(user));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
