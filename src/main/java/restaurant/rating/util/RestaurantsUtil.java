package restaurant.rating.util;

import restaurant.rating.model.Restaurant;
import restaurant.rating.to.RestaurantWithVotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantsUtil {
    public static List<RestaurantWithVotes> convertToRestaurantWithVotes(List<Restaurant> withDishes, List<Restaurant> withVotes) {

        List<RestaurantWithVotes> result = new ArrayList<>(  );

        Map<Integer, Integer> restaurantVotesMap = new HashMap<>();

        for (Restaurant restaurant : withVotes) {
            restaurantVotesMap.put(restaurant.getId(), restaurant.getVotes().size());
        }

        for (Restaurant restaurant : withDishes) {
            result.add(
                    new RestaurantWithVotes(
                            restaurant,
                            restaurantVotesMap.getOrDefault(restaurant.getId(), 0)
            ));
        }
        return result;
    }
}
