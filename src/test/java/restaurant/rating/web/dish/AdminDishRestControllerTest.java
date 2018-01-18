package restaurant.rating.web.dish;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import restaurant.rating.TestUtil;
import restaurant.rating.model.Dish;
import restaurant.rating.to.DishTo;
import restaurant.rating.web.AbstractRestControllerTest;
import restaurant.rating.web.json.JsonUtil;
import restaurant.rating.web.restaurant.AdminRestaurantRestController;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rating.TestUtil.contentJson;
import static restaurant.rating.TestUtil.userHttpBasic;
import static restaurant.rating.testdata.DishTestData.*;
import static restaurant.rating.testdata.RestaurantTestData.RESTAURANT1_ID;
import static restaurant.rating.testdata.UserTestData.ADMIN;
import static restaurant.rating.util.DishUtil.asTo;
import static restaurant.rating.util.DishUtil.createNewFromTo;

public class AdminDishRestControllerTest extends AbstractRestControllerTest {

    private final static String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Test
    public void testAdminDishGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT1_ID + "/dish/" + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(DISH1)));

    }

    @Test
    public void testAdminDishDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT1_ID + "/dish/" + DISH3_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Dish> dishes = restaurantRepository.getWithDishesByDate(RESTAURANT1_ID, DISHES_DATE).getDishes();
        assertMatch(dishes, DISH1, DISH2);
    }

    @Test
    public void testAdminDishCreate() throws Exception {
        Dish expected = new Dish(null, "Рассольник", DISHES_DATE, 15000);

        ResultActions action = mockMvc.perform(post(REST_URL + RESTAURANT1_ID + "/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(asTo(expected))))
                .andExpect(status().isCreated());

        DishTo to = TestUtil.readFromJson(action, DishTo.class);
        Dish returned = createNewFromTo(to);
        returned.setId(to.getId());
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        List<Dish> dishes = restaurantRepository.getWithDishesByDate(RESTAURANT1_ID, DISHES_DATE).getDishes();
        assertMatch(dishes, DISH1, DISH2, DISH3, returned);
    }

    @Test
    public void testAdminDishUpdate() throws Exception {
        Dish updated = new Dish(DISH2);
        updated.setPrice(22580);

        mockMvc.perform(put(REST_URL + RESTAURANT1_ID + "/dish/" + DISH2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(asTo(updated))))
                .andExpect(status().isOk());

        assertMatch(dishRepository.get(DISH2_ID, RESTAURANT1_ID), updated);
    }

}