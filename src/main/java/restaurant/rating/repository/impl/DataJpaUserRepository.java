package restaurant.rating.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.User;
import restaurant.rating.repository.CrudUserRepository;
import restaurant.rating.to.UserTo;
import restaurant.rating.util.UserUtil;

import java.util.List;

@Repository
@AllArgsConstructor
public class DataJpaUserRepository {

    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");
    private final CrudUserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User save(UserTo userTo) {
        User user = get(userTo.getId());
        return repository.save(UserUtil.updateFromTo(user, userTo));
    }

    // false if not found
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    // null if not found
    public User get(int id) {
        return repository.findById(id).orElse(null);
    }

    // null if not found
    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    // null if not found
    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }
}
