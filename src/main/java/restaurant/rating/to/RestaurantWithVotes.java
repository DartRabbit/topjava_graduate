package restaurant.rating.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import restaurant.rating.model.Restaurant;

@EqualsAndHashCode(exclude = {"votes"})
public class RestaurantWithVotes {
    @Getter
    private final Restaurant restaurant;
    @Getter
    private final int votes;

    public RestaurantWithVotes(Restaurant restaurant, int votes){
        this.restaurant=restaurant;
        this.votes = votes;
    }
}
