package restaurant.rating.testdata;

import restaurant.rating.model.Restaurant;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static restaurant.rating.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final int RESTAURANT1_ID = START_SEQ + 5;
    public static final int RESTAURANT2_ID = START_SEQ + 6;
    public static final int RESTAURANT3_ID = START_SEQ + 7;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID,"Ёлки-Палки");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID,"Fourth Michelin Star");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID,"Три пескаря");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "votes").isEqualTo(expected);
    }

    public static void assertMatch(boolean actual, boolean expected){
        assertThat(actual).isEqualTo(expected);
    }

}
