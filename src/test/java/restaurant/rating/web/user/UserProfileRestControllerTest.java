package restaurant.rating.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import restaurant.rating.model.User;
import restaurant.rating.to.UserTo;
import restaurant.rating.util.UserUtil;
import restaurant.rating.web.AbstractRestControllerTest;
import restaurant.rating.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static restaurant.rating.TestUtil.contentJson;
import static restaurant.rating.TestUtil.userHttpBasic;
import static restaurant.rating.testdata.UserTestData.*;
import static restaurant.rating.util.UserUtil.asTo;

public class UserProfileRestControllerTest extends AbstractRestControllerTest {
    private static final String REST_URL = UserProfileRestController.REST_URL + '/';

    @Test
    public void testUserProfileGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(asTo(USER1)));
    }

    @Test
    public void testUserProfileGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUserProfileDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userRepository.getAll(), ADMIN, USER2, USER3);
    }

    @Test
    public void testUserProfileUpdate() throws Exception {
        UserTo updated = new UserTo(100000, "newName", "newmail@yandex.ru", "newpass");

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(userRepository.getByEmail("newmail@yandex.ru"), UserUtil.updateFromTo(new User(USER1), updated));
    }
}