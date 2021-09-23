package slightlyspring.imgo.domain.til.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import slightlyspring.imgo.domain.til.repository.TilRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TilRepository tilRepository;

    @Test
    @WithMockUser(roles = "USER")
    void write() throws Exception {
        String WRITE_API_URI = "/til/write";

        mockMvc.perform(get(WRITE_API_URI))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("/til/write"))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void save() throws Exception {
        String SAVE_API_URI = "/til/save/";

        mockMvc.perform(post(SAVE_API_URI))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(view().name("/til/detail"))
            .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getTils() throws Exception {
        String GET_LIST_API_URI = "/til/1/til-cards";

        MvcResult mvcResult = mockMvc.perform(get(GET_LIST_API_URI))
            .andExpect(status().isOk())
            .andReturn();
        
        String contentType = mvcResult.getResponse().getContentType();
        Assertions.assertThat(contentType).contains("json");

        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).contains("title");
        Assertions.assertThat(content).contains("likeCount");
        Assertions.assertThat(content).contains("createdAt");
        Assertions.assertThat(content).contains("tags");
        Assertions.assertThat(content).contains("nickname");
    }
}