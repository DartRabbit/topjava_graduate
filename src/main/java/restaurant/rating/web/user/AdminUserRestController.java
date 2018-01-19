package restaurant.rating.web.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rating.model.User;
import restaurant.rating.repository.impl.DataJpaUserRepository;

import java.net.URI;
import java.util.List;

import static restaurant.rating.util.ValidationUtil.*;
import static restaurant.rating.web.user.AdminUserRestController.REST_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(REST_URL)
public class AdminUserRestController {
    protected static final String REST_URL = "/rest/admin/users";
    private final DataJpaUserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.info("get all users");
        return repository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        log.info("get users {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete user {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody User user) {
        Assert.notNull(user, "user must not be null");
        checkNew(user);
        User created = repository.save(user);
        log.info("create user {}", user.getEmail());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user, @PathVariable("id") int id) {
        log.info("update user {}", id);
        assureIdConsistent(user, id);
        repository.save(user);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByMail(@RequestParam("email") String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }
}
