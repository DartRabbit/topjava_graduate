package restaurant.rating.web.restaurant;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import restaurant.rating.model.Restaurant;
import restaurant.rating.web.AbstractRestControllerTest;
import restaurant.rating.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rating.TestUtil.*;
import static restaurant.rating.testdata.RestaurantTestData.*;
import static restaurant.rating.testdata.UserTestData.ADMIN;

public class AdminRestaurantRestControllerTest extends AbstractRestControllerTest {

    private final static String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Test
    public void testAdminRestaurantGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonArray(RESTAURANT2, RESTAURANT1, RESTAURANT3));
    }

    @Test
    public void testAdminRestaurantGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT1));
    }

    @Test
    public void testAdminRestaurantGetByName() throws Exception {
        mockMvc.perform(get(REST_URL + "by?name=" + RESTAURANT1.getName())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT1));
    }

    @Test
    public void testAdminRestaurantGetAllWithDishesToday() throws Exception {
        mockMvc.perform(get(REST_URL + "with_dishes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAdminRestaurantGetAllWithDishesByDate() throws Exception {
        mockMvc.perform(get(REST_URL + "with_dishes?date=" + LocalDate.of(2017, 12, 29).toString())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAdminRestaurantDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantRepository.getAll(), RESTAURANT2, RESTAURANT3);
    }

    @Test
    public void testAdminRestaurantCreate() throws Exception {
        Restaurant expected = new Restaurant(null, "Чижик-Пыжик");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(restaurantRepository.getAll(), RESTAURANT2, RESTAURANT1, RESTAURANT3, expected);
    }

    @Test
    public void testAdminRestaurantUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(restaurantRepository.get(RESTAURANT1_ID), updated);
    }
}