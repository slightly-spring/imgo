package slightlyspring.imgo.domain.user.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import static org.hamcrest.Matchers.containsString;
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
    @DisplayName("비회원 -> 프로필 조회")
    void profile() throws Exception {
        //given
        User testUser = User.builder()
                            .nickname("nickNameA")
                            .profileImg("ImgPathA")
                            .build();
        User savedUser = userRepository.save(testUser);

        String PROFILE_API_URI = "/user/";

        // when
        // then
        Assertions.assertTrue(mockMvc.perform(
            get(PROFILE_API_URI + savedUser.getId())
        )
            .andExpect(status().isOk())
            .andExpect(content().string(
                    containsString(savedUser.getProfileImg())))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .contains(savedUser.getNickname()));
    }
}