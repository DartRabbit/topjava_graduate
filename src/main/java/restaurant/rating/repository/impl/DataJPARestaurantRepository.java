package restaurant.rating.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import restaurant.rating.model.Restaurant;
import restaurant.rating.repository.CrudRestaurantRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class DataJPARestaurantRepository {
    public static final Sort SORT_NAME = new Sort(Sort.Direction.ASC, "name");

    @Autowired
    CrudRestaurantRepository repository;

    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    public Restaurant getByName(String name) {
        return repository.findByName(name);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    public List<Restaurant> getPage(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public List<Restaurant> getAllWithDishes(LocalDate date) {
        return repository.getAllWithDishes(LocalDateTime.of(date, LocalTime.MIN));
    }
    public Restaurant getWithDishes(int id, LocalDate date){
        return repository.getWithDishes(id, LocalDateTime.of(date, LocalTime.MIN));
    }
}
