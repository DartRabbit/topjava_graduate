package restaurant.rating.web.user;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(REST_URL)
public class UserProfileRestController {
    protected static final String REST_URL = "/rest/profile";
    private final DataJpaUserRepository repository;

    @Autowired
    public UserProfileRestController(DataJpaUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get() {
        return asTo(repository.get(AuthorizedUser.id()));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete() {
        repository.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserTo userTo) {
        assureIdConsistent(repository.save(userTo), AuthorizedUser.id());
    }
}
