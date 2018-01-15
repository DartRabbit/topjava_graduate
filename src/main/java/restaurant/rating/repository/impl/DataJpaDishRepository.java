package restaurant.rating.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.Dish;
import restaurant.rating.model.Restaurant;
import restaurant.rating.repository.CrudDishRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DataJpaDishRepository {

    private final CrudDishRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public DataJpaDishRepository(CrudDishRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean delete(int id, int restaurantId) {
        return repository.delete(id, restaurantId) != 0;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Dish save(Dish dish, int restaurantId) {
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        return repository.save(dish);
    }

    public Dish get(int id, int restaurantId) {
        return repository.getDishByIdAndRestaurantId(id, restaurantId);
    }
}
