package restaurant.rating.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rating.model.Dish;
import restaurant.rating.repository.impl.DataJpaDishRepository;

import java.net.URI;

import static restaurant.rating.util.ValidationUtil.*;
import static restaurant.rating.web.restaurant.AdminRestaurantRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class AdminDishRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DataJpaDishRepository repository;

    @Autowired
    AdminDishRestController(DataJpaDishRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable("id") int id) {
        log.info("get dish{}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @DeleteMapping("/{restaurantId}/dish/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        log.info("delete dish {} from restaurant {}", dishId, restaurantId);
        checkNotFoundWithId(repository.delete(dishId, restaurantId), dishId);
    }

    @PostMapping(value = "/{restaurantId}/dish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@PathVariable("restaurantId") int restaurantId, @RequestBody Dish dish) {
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);

        Dish created = repository.save(dish, restaurantId);
        String url_string = REST_URL + "/" + restaurantId + "dish";

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url_string + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/dish/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId, @RequestBody Dish dish) {
        log.info("update dish {} for restaurant {}", dish, restaurantId);
        assureIdConsistent(dish, dishId);
        repository.save(dish, restaurantId);
    }
}
