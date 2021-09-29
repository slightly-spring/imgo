package slightlyspring.imgo.domain.til.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.util.StringUtils;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import org.json.*;

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

    private ObjectMapper objectMapper= new ObjectMapper();

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
                .andExpect(view().name("/til/{tilId}"))
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