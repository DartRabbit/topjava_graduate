package restaurant.rating.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import restaurant.rating.model.User;
import restaurant.rating.repository.UserRepository;

import java.util.List;
import static restaurant.rating.testdata.UserTestData.*;

public class DataJPAUserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void create() throws Exception {
        User user = new User(null, "User4", "user4@yandex.ru", "password", false);
        User saved = userRepository.save(user);
        user.setId(saved.getId());
        assertMatch(user, saved);
    }

    @Test
    public void update() throws Exception {
        User user = new User(USER2);
        user.setEmail("user2@gmail.com");
        User updated = userRepository.save(user);
        assertMatch(user, updated);
    }

    @Test
    public void delete() throws Exception {
        userRepository.delete(ADMIN_ID);
        List<User> users = userRepository.getAll();
        assertMatch(users, USER1, USER2, USER3);
    }

    @Test
    public void deleteNotFound() throws Exception{
        assertMatch(userRepository.delete(1),false);
    }

    @Test
    public void get() throws Exception {
        User user = userRepository.get(100000);
        assertMatch(user, USER1);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = userRepository.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = userRepository.getAll();
        assertMatch(users, ADMIN, USER1, USER2, USER3);
    }

}