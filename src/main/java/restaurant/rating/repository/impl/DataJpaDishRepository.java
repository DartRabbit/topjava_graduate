package restaurant.rating.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.Dish;
import restaurant.rating.model.Restaurant;
import restaurant.rating.repository.CrudDishRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DataJpaDishRepository {

    @Autowired
    private CrudDishRepository repository;

    @PersistenceContext
    private EntityManager em;

    public boolean delete(int id, int restaurantId) {
        return repository.delete(id, restaurantId) != 0;
    }

    public Dish save(Dish dish, int restaurantId) {
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        return repository.save(dish);
    }
}
