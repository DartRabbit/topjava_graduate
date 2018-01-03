package restaurant.rating.testdata;

import restaurant.rating.model.User;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static restaurant.rating.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int USER3_ID = START_SEQ + 2;
    public static final int ADMIN_ID = START_SEQ + 3;

    public static final User USER1 = new User(USER1_ID,"User1","user1@yandex.ru","password",false);
    public static final User USER2 = new User(USER2_ID,"User2","user2@yandex.ru","password",false);
    public static final User USER3 = new User(USER3_ID,"User3","user3@yandex.ru","password", false);
    public static final User ADMIN = new User(ADMIN_ID,"Admin","admin@gmail.com","admin", true);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes").isEqualTo(expected);
    }

    public static void assertMatch(boolean actual, boolean expected){
        assertThat(actual).isEqualTo(expected);
    }

//    public static ResultMatcher contentJson(User... expected) {
//        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered"));
//    }
//
//    public static ResultMatcher contentJson(User expected) {
//        return content().json(writeIgnoreProps(expected, "registered"));
//    }
}
