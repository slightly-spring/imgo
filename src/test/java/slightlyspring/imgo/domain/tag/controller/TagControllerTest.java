package slightlyspring.imgo.domain.tag.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.service.TagService;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagService tagService;

    @Test
    void 태그_whitelist_성공() throws Exception {
        // given
        List<String> tagList = Arrays.asList("spring", "spring boot", "spring cloud", "spring1", "spring2", "spring123", "spring3", "토비의 spring", "spring data");
        List<Tag> savedTags = tagService.saveTags(tagList);

        // when
        String TAG_SUGGESTIONS_URL = "/tag/suggestions?value=";
        String value = "spring";

        // then
        mockMvc.perform(get(TAG_SUGGESTIONS_URL + value))
                .andExpect(status().isOk())
                .andExpect(content().json(tagList.toString()));
    }

    @Test
    void 태그_whitelist_empty() throws Exception {
        // given
        List<String> tagList = Arrays.asList("spring", "spring boot", "spring cloud", "spring1", "spring2", "spring123", "spring3", "토비의 spring", "spring data");
        List<Tag> savedTags = tagService.saveTags(tagList);

        // when
        String TAG_SUGGESTIONS_URL = "/tag/suggestions?value=";
        String value = "";

        // then
        mockMvc.perform(get(TAG_SUGGESTIONS_URL + value))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}