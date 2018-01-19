package restaurant.rating.web.restaurant;

import org.junit.Test;
import restaurant.rating.web.AbstractRestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rating.TestUtil.userHttpBasic;
import static restaurant.rating.testdata.RestaurantTestData.RESTAURANT1_ID;
import static restaurant.rating.testdata.UserTestData.USER1;
import static restaurant.rating.testdata.UserTestData.USER4;

public class UserRestaurantRestControllerTest extends AbstractRestControllerTest {

    private final static String REST_URL = UserRestaurantRestController.REST_URL + '/';

    @Test
    public void testUserRestaurantVoteTodaySuccess() throws Exception {
        mockMvc.perform(post(REST_URL + RESTAURANT1_ID + "/vote")
                .with(userHttpBasic(USER4)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testUserRestaurantVoteTodayFail() throws Exception {
        mockMvc.perform(post(REST_URL + RESTAURANT1_ID + "/vote")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isNotAcceptable())
                .andDo(print());
    }
}