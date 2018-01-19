package restaurant.rating.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import restaurant.rating.model.Restaurant;
import restaurant.rating.model.Vote;
import restaurant.rating.model.VoteId;
import restaurant.rating.repository.impl.DataJpaRestaurantRepository;
import restaurant.rating.repository.impl.DataJpaVoteRepository;
import restaurant.rating.security.AuthorizedUser;
import restaurant.rating.util.exception.NotAvailableRevoteException;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static restaurant.rating.web.restaurant.UserRestaurantRestController.REST_URL;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(REST_URL)
public class UserRestaurantRestController {
    protected static final String REST_URL = "/rest/restaurants";
    private static final LocalTime END_OF_VOTING = LocalTime.of(11, 0, 0);
    private final DataJpaRestaurantRepository restaurantRepository;
    private final DataJpaVoteRepository voteRepository;

    @PostMapping(value = "/{id}/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@PathVariable("id") int id) {
        LocalDate voteDate = LocalDate.now();
        VoteId voteId = new VoteId(AuthorizedUser.id(), voteDate);
        Vote vote = new Vote(voteId, voteDate);

        if (LocalDateTime.now().isBefore(LocalDateTime.of(voteDate, END_OF_VOTING))) {
            log.info("vote for restaurant {} on {} by user {}", id, LocalDate.now(), AuthorizedUser.id());
            Vote voteCreated = voteRepository.save(vote, id, AuthorizedUser.id());
            return ResponseEntity.ok(voteCreated);
        }else{
            if(voteRepository.get(voteId)==null){
                Vote voteCreated = voteRepository.save(vote, id, AuthorizedUser.id());
                return ResponseEntity.ok(voteCreated);
            }
            throw new NotAvailableRevoteException(String.format("Revote after %s by user %d", END_OF_VOTING.toString(), AuthorizedUser.id()));
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllWithDishes(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate queryDate = date != null ? date : LocalDate.now();
        log.info("getAllWithVotes by date {}", queryDate);
        return restaurantRepository.getAllWithDishesByDate(queryDate);
    }
}
