package restaurant.rating.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rating.model.Restaurant;
import restaurant.rating.repository.impl.DataJPARestaurantRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static restaurant.rating.repository.impl.DataJPARestaurantRepository.SORT_NAME;
import static restaurant.rating.util.ValidationUtil.*;
import static restaurant.rating.web.restaurant.AdminRestaurantRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class AdminRestaurantRestController {
    public static final String REST_URL = "/rest/admin/restaurants";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataJPARestaurantRepository restaurantRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.getAll();
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getPage(
            @RequestParam("num") int num,
            @RequestParam("size") int size) {
        log.info("getPage: num-{}, size-{}", num, size);
        PageRequest request = PageRequest.of(num, size, SORT_NAME);
        return restaurantRepository.getPage(request);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @GetMapping(value = "/with_dishes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getWithDishes(
            @PathVariable("id") int id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        log.info("getWithDishes {} by date {}", id, date);
        return checkNotFoundWithId(restaurantRepository.getWithDishes(id, date), id);
    }

    @GetMapping(value = "/with_dishes",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllWithDishes(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getAllWithDishes by date {}", date);
        return restaurantRepository.getAllWithDishes(date);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam("name") String name) {
        log.info("getByName {}", name);
        Assert.notNull(name, "name must not be null");
        return checkNotFound(restaurantRepository.getByName(name), "name = " + name);
    }

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
