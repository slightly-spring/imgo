package slightlyspring.imgo.domain.til.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TilControllerTest {

    @Autowired
    private MockMvc mockMvc;
    public MockHttpSession mockSession;  // FIXME write 테스트에서 세션을 가져올 수 없음


    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper= new ObjectMapper();

    @BeforeAll
    void setUp() {
        User user = User.builder().nickname("test").build();
        User savedUser = userRepository.save(user);
        mockSession = new MockHttpSession();
        mockSession.setAttribute("userId", savedUser.getId());
    }

    @AfterAll
    void clean(){
        mockSession.clearAttributes();
    }


    @Test
    @WithMockUser(roles = "USER")
    void write() throws Exception {
        String WRITE_API_URI = "/til/write";

        mockMvc.perform(get(WRITE_API_URI)
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attributeExists("seriesList"))
                .andExpect(view().name("/til/write"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void save() throws Exception {
        String SAVE_API_URI = "/til/save/";

        mockMvc.perform(post(SAVE_API_URI))
                .andExpect(status().isOk())
                .andExpect(view().name("/til/detail"))
                .andDo(print());
    }

    @Test
    @DisplayName("디폴트 설정으로 페이징된 til을 가져온다")
    @WithMockUser(roles = "USER")
    void getTils_defaultPaging() throws Exception {
        int DEFAULT_SIZE = 5;
        String GET_PAGE_API_URI = "/til/1/til-cards";

        MvcResult mvcResult = mockMvc.perform(get(GET_PAGE_API_URI))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        String contentType = mvcResult.getResponse().getContentType();
        Assertions.assertThat(contentType).contains("json");

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("content = " + content);

        // String 을 java object 로 어케 바꾸지;
        // string -> json object -> java object ??
    }
}