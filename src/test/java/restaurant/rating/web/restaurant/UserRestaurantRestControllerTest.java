package restaurant.rating.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import restaurant.rating.web.AbstractRestControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rating.TestUtil.userHttpBasic;
import static restaurant.rating.testdata.RestaurantTestData.RESTAURANT1_ID;
import static restaurant.rating.testdata.UserTestData.USER1;

public class UserRestaurantRestControllerTest extends AbstractRestControllerTest {

    private final static String REST_URL = UserRestaurantRestController.REST_URL + '/';

    @Test
    public void testVoteToday() throws Exception {
        mockMvc.perform(post(REST_URL + RESTAURANT1_ID + "/vote")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllWithVotesToday() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetAllWithVotesByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "?date=" + LocalDate.of(2017, 12, 29).toString())
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}