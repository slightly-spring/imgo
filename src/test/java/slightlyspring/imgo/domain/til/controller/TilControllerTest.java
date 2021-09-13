package slightlyspring.imgo.domain.til.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import slightlyspring.imgo.domain.til.repository.TilRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void write() throws Exception {
        String WRITE_API_URI = "/user/profile/";

        mockMvc.perform(get(WRITE_API_URI))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("write"))
                .andDo(print());
    }
}