package slightlyspring.imgo.domain.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void profile() throws Exception {
        String PROFILE_API_URI = "/user/profile/";
        Long testUserId = 1L;
        User user = userRepository.getById(testUserId);

        mockMvc.perform(
                        get(PROFILE_API_URI + testUserId))
                .andExpect(status().isOk())
                .andExpect(content().string(user.getNickname()))
                .andExpect(content().string(user.getProfileImg()))
                .andExpect(content().string(user.getProfileDescription()))
                .andDo(print()
                );
    }
}