package restaurant.rating.repository.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import restaurant.rating.model.Restaurant;

import java.util.List;

import static restaurant.rating.repository.impl.DataJPARestaurantRepository.SORT_NAME;
import static restaurant.rating.testdata.RestaurantTestData.*;

public class RestaurantRepositoryTest extends AbstractRepositoryTest {

    public static final int PAGE_SIZE = 2;

    @Autowired
    DataJPARestaurantRepository repository;

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

    @Test
    public void getPage() throws Exception {
        PageRequest request1 = PageRequest.of(0, PAGE_SIZE, SORT_NAME);
        PageRequest request2 = PageRequest.of(1, PAGE_SIZE, SORT_NAME);
        List<Restaurant> restaurantList1 = repository.getPage(request1);
        List<Restaurant> restaurantList2 = repository.getPage(request2);

        assertMatch(restaurantList1, RESTAURANT2, RESTAURANT1);
        assertMatch(restaurantList2, RESTAURANT3);
    }

}