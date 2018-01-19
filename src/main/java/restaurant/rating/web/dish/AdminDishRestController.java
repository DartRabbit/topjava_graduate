package restaurant.rating.web.dish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rating.model.Dish;
import restaurant.rating.repository.impl.DataJpaDishRepository;
import restaurant.rating.to.DishTo;

import java.net.URI;

import static restaurant.rating.util.DishUtil.asTo;
import static restaurant.rating.util.DishUtil.createNewFromTo;
import static restaurant.rating.util.DishUtil.updateFromTo;
import static restaurant.rating.util.ValidationUtil.*;
import static restaurant.rating.web.restaurant.AdminRestaurantRestController.REST_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(REST_URL)
public class AdminDishRestController {
    private final DataJpaDishRepository repository;

    @GetMapping(value = "/{restaurantId}/dish/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DishTo get(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        log.info("get dish {} from restaurant {}", id, restaurantId);
        return asTo(checkNotFoundWithId(repository.get(id, restaurantId), id));
    }

    @DeleteMapping("/{restaurantId}/dish/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        log.info("delete dish {} from restaurant {}", dishId, restaurantId);
        checkNotFoundWithId(repository.delete(dishId, restaurantId), dishId);
    }

    @PostMapping(value = "/{restaurantId}/dish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@PathVariable("restaurantId") int restaurantId, @RequestBody DishTo dishTo) {
        log.info("create dish {} for restaurant {}", dishTo.getName(), restaurantId);
        Assert.notNull(dishTo, "dish must not be null");
        checkNew(dishTo);

        Dish created = repository.save(createNewFromTo(dishTo), restaurantId);
        String url_string = REST_URL + "/" + restaurantId + "dish";

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url_string + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(asTo(created));
    }

    @PutMapping(value = "/{restaurantId}/dish/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId, @RequestBody DishTo dishTo) {
        log.info("update dish {} for restaurant {}", dishTo, restaurantId);
        assureIdConsistent(dishTo, dishId);
        Dish dish = repository.get(dishId, restaurantId);
        repository.save(updateFromTo(dish,dishTo), restaurantId);
    }
}
