package restaurant.rating.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restaurant.rating.AuthorizedUser;
import restaurant.rating.model.User;
import restaurant.rating.repository.impl.DataJPAUserRepository;

import static restaurant.rating.util.ValidationUtil.assureIdConsistent;
import static restaurant.rating.web.UserProfileRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class UserProfileRestController {
    static final String REST_URL = "/rest/profile";
    @Autowired
    private DataJPAUserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return repository.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete() {
        repository.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        assureIdConsistent(repository.save(user), AuthorizedUser.id());
    }


}
