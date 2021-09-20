package slightlyspring.imgo.domain.user.controller;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // resolve the 'LazyInitializationException'
class UserControllerTest {

    // add mockMVC with Security Context
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach // inject the security context to mockMVC
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
//    @WithMockUser(roles = "USER") // temporary authorized user for mockMVC
    void profile() throws Exception {
        // user In-memory DB, so should add new data before test
        LocalDateTime time = LocalDateTime.now();
        User testUser = new User().builder()
                            .id(1L)
                            .nickname("nickNameA")
                            .profileImg("ImgPathA")
                            .profileDescription("DescriptionA")
                            .build();
        userRepository.save(testUser);

        String PROFILE_API_URI = "/user/profile/";
        Long testUserId = 1L;
        User user = userRepository.getById(testUserId);

        Assertions.assertTrue(mockMvc.perform(
            get(PROFILE_API_URI + testUserId)
        )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString()
            .contains(user.getNickname()));
//                .andExpect(content().string(user.getNickname()))
//                .andExpect(content().string(user.getProfileImg()))
//                .andExpect(content().string(user.getProfileDescription()))
//                .andDo(print());
    }
}