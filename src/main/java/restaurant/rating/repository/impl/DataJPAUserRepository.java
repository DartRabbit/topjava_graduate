package restaurant.rating.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.User;
import restaurant.rating.repository.CrudUserRepository;


import java.util.List;

@Repository
public class DataJPAUserRepository {

    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository repository;

    public User save(User user) {
        return repository.save(user);
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
