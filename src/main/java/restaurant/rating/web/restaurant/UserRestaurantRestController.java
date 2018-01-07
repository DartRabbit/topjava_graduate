package restaurant.rating.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restaurant.rating.model.Restaurant;
import restaurant.rating.repository.impl.DataJPARestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static restaurant.rating.web.restaurant.UserRestaurantRestController.REST_URL;

@RestController
@RequestMapping(REST_URL)
public class UserRestaurantRestController {
    static final String REST_URL = "/rest/restaurants";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataJPARestaurantRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllWithDishes(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {

        if(date==null){
            date= LocalDate.now();
        }
        log.info("getAllWithDishes by date {}", date);
        return repository.getAllWithDishes(date);
    }
}
