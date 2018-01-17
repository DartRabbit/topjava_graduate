package restaurant.rating.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import restaurant.rating.model.Dish;
import restaurant.rating.model.Restaurant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static restaurant.rating.util.DishUtil.asTo;

@EqualsAndHashCode(callSuper = true, exclude = {"votes", "name", "dishes"})
public class RestaurantWithVotes extends BaseTo {
    @Getter
    private final int votes;
    @Getter
    private final String name;
    @Getter
    private final List<DishTo> dishes;

    public RestaurantWithVotes(Restaurant restaurant, int votes){
        super(restaurant.getId());
        this.name=restaurant.getName();
        this.dishes=new LinkedList<>();
        for (Dish dish: restaurant.getDishes()) {
            this.dishes.add(asTo(dish));
        }
        this.votes = votes;
    }
}
