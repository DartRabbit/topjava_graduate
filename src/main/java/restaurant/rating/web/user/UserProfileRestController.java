package restaurant.rating.web.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restaurant.rating.security.AuthorizedUser;
import restaurant.rating.repository.impl.DataJpaUserRepository;
import restaurant.rating.to.UserTo;

import static restaurant.rating.util.UserUtil.asTo;
import static restaurant.rating.util.ValidationUtil.assureIdConsistent;
import static restaurant.rating.web.user.UserProfileRestController.REST_URL;

@RestController
@Slf4j
@RequestMapping(REST_URL)
@AllArgsConstructor
public class UserProfileRestController {
    protected static final String REST_URL = "/rest/profile";
    private final DataJpaUserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get() {
        log.info("get user profile {}", AuthorizedUser.id());
        return asTo(repository.get(AuthorizedUser.id()));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete() {
        log.info("delete user profile {}",AuthorizedUser.id());
        repository.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserTo userTo) {
        log.info("update user profile {}",AuthorizedUser.id());
        assureIdConsistent(repository.save(userTo), AuthorizedUser.id());
    }
}
