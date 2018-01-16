package restaurant.rating.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import restaurant.rating.model.User;

import java.util.List;
import static restaurant.rating.testdata.UserTestData.*;

public class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DataJPAUserRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() throws Exception {
        User user = new User(null, "User4", "user4@yandex.ru", "password", false);
        User saved = repository.save(user);
        user.setId(saved.getId());
        assertMatch(user, saved);
    }

    @Test
    public void update() throws Exception {
        User user = new User(USER2);
        user.setEmail("user2@gmail.com");
        User updated = repository.save(user);
        assertMatch(user, updated);
    }

    @Test
    public void delete() throws Exception {
        repository.delete(ADMIN_ID);
        List<User> users = repository.getAll();
        assertMatch(users, USER1, USER2, USER3);
    }

    @Test
    public void deleteNotFound() throws Exception{
        assertMatch(repository.delete(1),false);
    }

    @Test
    public void get() throws Exception {
        User user = repository.get(100000);
        assertMatch(user, USER1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = repository.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = repository.getAll();
        assertMatch(users, ADMIN, USER1, USER2, USER3);
    }

}