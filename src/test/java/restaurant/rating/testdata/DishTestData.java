package restaurant.rating.testdata;

import restaurant.rating.model.Dish;

import java.time.LocalDate;
import java.util.Arrays;

import static restaurant.rating.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 7;
    public static final int DISH2_ID = START_SEQ + 8;
    public static final int DISH3_ID = START_SEQ + 9;
    public static final LocalDate DISHES_DATE = LocalDate.of(2017, 12, 29);

    public static final Dish DISH1 = new Dish(DISH1_ID, "Оливье", DISHES_DATE, 13025);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Холодец", DISHES_DATE, 18630);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Мандаринка", DISHES_DATE, 6000);

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static void assertMatch(boolean actual, boolean expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
