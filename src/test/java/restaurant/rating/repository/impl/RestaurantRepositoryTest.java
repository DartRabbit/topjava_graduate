package restaurant.rating.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import restaurant.rating.model.Restaurant;

import java.util.List;

import static restaurant.rating.testdata.RestaurantTestData.*;

public class RestaurantRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DataJpaRestaurantRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void create() throws Exception {
        Restaurant restaurant = new Restaurant(null, "Чебуречная");
        Restaurant saved = repository.save(restaurant);
        restaurant.setId(saved.getId());
        assertMatch(restaurant, saved);
    }

    @Test
    public void update() throws Exception {
        Restaurant restaurant = new Restaurant(RESTAURANT3);
        restaurant.setName("Плакучая ива");
        Restaurant updated = repository.save(restaurant);
        assertMatch(restaurant, updated);
    }

    @Test
    public void delete() throws Exception {
        repository.delete(RESTAURANT1_ID);
        List<Restaurant> restaurants = repository.getAll();
        assertMatch(restaurants, RESTAURANT2, RESTAURANT3);
    }

    @Test
    public void deleteNotFound() throws Exception {
        assertMatch(repository.delete(1), false);
    }

    @Test
    public void get() throws Exception {
        Restaurant restaurant = repository.get(100006);
        assertMatch(restaurant, RESTAURANT3);
    }

    @Test
    public void getByName() throws Exception {
        Restaurant restaurant = repository.getByName("Fourth Michelin Star");
        assertMatch(restaurant, RESTAURANT2);
    }

    @Test
    public void getAll() throws Exception {
        List<Restaurant> restaurants = repository.getAll();
        assertMatch(restaurants, RESTAURANT2, RESTAURANT1, RESTAURANT3);
    }
}