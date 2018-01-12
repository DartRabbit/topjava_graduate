package restaurant.rating.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restaurant.rating.security.AuthorizedUser;
import restaurant.rating.model.User;
import restaurant.rating.repository.impl.DataJPAUserRepository;
import restaurant.rating.to.UserTo;

import static restaurant.rating.util.ValidationUtil.assureIdConsistent;
import static restaurant.rating.web.user.UserProfileRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class UserProfileRestController {
    protected static final String REST_URL = "/rest/profile";
    private final DataJPAUserRepository repository;

    @Autowired
    public UserProfileRestController(DataJPAUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return repository.get(AuthorizedUser.id());
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
