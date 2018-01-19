package restaurant.rating.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.Restaurant;
import restaurant.rating.repository.CrudRestaurantRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository {

    public static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");
    private final CrudRestaurantRepository repository;

    @Autowired
    public DataJpaRestaurantRepository(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    public Restaurant getByName(String name) {
        return repository.findByName(name);
    }

    @Cacheable("restaurants")
    public Restaurant getWithDishesByDate(int id, LocalDate date) {
        return repository.getWithDishesByDate(id, date);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllWithDishesByDate(LocalDate date) {
        return repository.getAllWithDishesByDate(date);
    }

    public List<Restaurant> getAllWithVotesByDate(LocalDate date) {
        return repository.getAllWithVotesByDate(date);
    }
}
