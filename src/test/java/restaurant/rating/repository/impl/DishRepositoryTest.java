package restaurant.rating.repository.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import restaurant.rating.model.Dish;

import java.util.List;

import static restaurant.rating.testdata.DishTestData.*;
import static restaurant.rating.testdata.RestaurantTestData.RESTAURANT1_ID;
import static restaurant.rating.testdata.RestaurantTestData.RESTAURANT3_ID;

public class DishRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private DataJpaDishRepository dishRepository;

    @Autowired
    private DataJPARestaurantRepository restaurantRepository;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
    }

    @Test
    public void delete() throws Exception {
        dishRepository.delete(DISH1_ID, RESTAURANT1_ID);
        List<Dish> dishes = restaurantRepository.getWithDishesByDate(RESTAURANT1_ID, DISHES_DATE).getDishes();
        assertMatch(dishes, DISH2, DISH3);
    }

    @Test
    public void deleteNotFound() throws Exception {
        assertMatch(dishRepository.delete(DISH2_ID,RESTAURANT3_ID), false);
    }

    @Test
    public void create() throws Exception {
        Dish newDish = new Dish(null, "Рассольник", DISHES_DATE, 15000);
        dishRepository.save(newDish, RESTAURANT1_ID);
        List<Dish> dishes = restaurantRepository.getWithDishesByDate(RESTAURANT1_ID, DISHES_DATE).getDishes();
        assertMatch(dishes, DISH1, DISH2, DISH3, newDish);
    }

    @Test
    public void update() throws Exception {
        Dish updated = new Dish(DISH2);
        updated.setPrice(22580);
        dishRepository.save(updated,RESTAURANT1_ID);
        assertMatch(updated,dishRepository.get(DISH2_ID,RESTAURANT1_ID));
    }

    @Test
    public void get() throws Exception {
        assertMatch(DISH3,dishRepository.get(DISH3_ID,RESTAURANT1_ID));
    }

}