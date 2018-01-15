package restaurant.rating.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rating.model.Restaurant;
import restaurant.rating.repository.impl.DataJPARestaurantRepository;
import restaurant.rating.to.RestaurantWithVotes;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static restaurant.rating.util.RestaurantsUtil.convertToRestaurantWithVotes;
import static restaurant.rating.util.ValidationUtil.*;
import static restaurant.rating.web.restaurant.AdminRestaurantRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class AdminRestaurantRestController {
    public static final String REST_URL = "/rest/admin/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DataJPARestaurantRepository restaurantRepository;

    @Autowired
    public AdminRestaurantRestController(DataJPARestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam("name") String name) {
        log.info("getByName {}", name);
        Assert.notNull(name, "name must not be null");
        return checkNotFound(restaurantRepository.getByName(name), "name = " + name);
    }

    @GetMapping(value = "/with_dishes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getWithDishes(
            @PathVariable("id") int id,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        date = date != null ? date : LocalDate.now();
        log.info("getWithDishes {} by date {}", id, date);
        return checkNotFoundWithId(restaurantRepository.getWithDishesByDate(id, date), id);
    }

    @GetMapping(value = "/with_dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllWithDishes(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        date = date != null ? date : LocalDate.now();
        log.info("getAllWithDishes by date {}", date);
        return restaurantRepository.getAllWithDishesByDate(date);
    }

    @GetMapping(value = "/with_votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVotes> getAllWithVotes(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        date = date != null ? date : LocalDate.now();
        log.info("getAllWithVotes by date {}", date);
        return convertToRestaurantWithVotes(restaurantRepository.getAllWithDishesByDate(date), restaurantRepository.getAllWithVotesByDate(date));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        log.info("update {}", id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }
}
