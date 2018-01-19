package restaurant.rating.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restaurant.rating.model.Vote;
import restaurant.rating.model.VoteId;
import restaurant.rating.repository.impl.DataJpaRestaurantRepository;
import restaurant.rating.repository.impl.DataJpaVoteRepository;
import restaurant.rating.security.AuthorizedUser;
import restaurant.rating.to.RestaurantWithVotes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static restaurant.rating.util.RestaurantsUtil.convertToRestaurantWithVotes;
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
    public Vote vote(@PathVariable("id") int id,
                     @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate voteDate = date != null ? date : LocalDate.now();

        if (LocalDateTime.now().isBefore(LocalDateTime.of(voteDate, END_OF_VOTING))) {
            log.info("vote for restaurant {} on {} by user {}", id, LocalDate.now(), AuthorizedUser.id());
            VoteId voteId = new VoteId(AuthorizedUser.id(), voteDate);
            Vote vote = new Vote(voteId, voteDate);
            return voteRepository.save(vote, id, AuthorizedUser.id());
        }
        return null;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithVotes> getAllWithVotes(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate queryDate = date != null ? date : LocalDate.now();
        log.info("getAllWithVotes by date {}", queryDate);
        return convertToRestaurantWithVotes(restaurantRepository.getAllWithDishesByDate(queryDate), restaurantRepository.getAllWithVotesByDate(queryDate));
    }
}
