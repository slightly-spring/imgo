package slightlyspring.imgo.domain.series.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.dto.SeriesCardData;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.series.service.SeriesService;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SeriesControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private SeriesRepository seriesRepository;

    private ObjectMapper objectMapper= new ObjectMapper();

    @Test
    void 시리즈입력_같은시리즈명_다른유저() {
        //given
        User user01 = User.builder().nickname("user01").build();
        User user02 = User.builder().nickname("user02").build();
        User savedUser01 = userRepository.save(user01);
        User savedUser02 = userRepository.save(user02);

        Series series01 = Series.builder()
                .title("Spring")
                .user(savedUser01)
                .build();

        Series series02 = Series.builder()
                .title("Spring")
                .user(savedUser02)
                .build();
        
        //when
        seriesService.saveSeries(series01);
        seriesService.saveSeries(series02);

        //then
        assertThat(seriesService.getMySeries(user01.getId()).size()).isEqualTo(1);
        assertThat(seriesService.getMySeries(user02.getId()).size()).isEqualTo(1);

    }

    @Test
    @DisplayName("디폴트 설정으로 페이징된 series를 가져온다")
    @WithMockUser(roles = "USER")
    void getSeries_defaultPaging() throws Exception {
        User user = initUser();
        Series series = initSeries(user);
        Long userId = user.getId();
        String GET_PAGE_API_URI = "/series/"+userId+"/series-cards";

        MvcResult mvcResult = mockMvc.perform(get(GET_PAGE_API_URI))
            .andExpect(status().isOk())
//            .andDo(print())
            .andReturn();

        String contentType = mvcResult.getResponse().getContentType();
        assertThat(contentType).contains("json");

        String content = mvcResult.getResponse().getContentAsString();

        List<SeriesCardData> seriesCardDataList
            = objectMapper.readValue(content, new TypeReference<>() {});
        SeriesCardData seriesCardData = seriesCardDataList.get(0);
        assertThat(seriesCardData.getTitle()).isEqualTo(series.getTitle());


    }

    /* Init Entity */
    private User initUser() {
        User user = User.builder().nickname("userName").build();
        return userRepository.save(user);    }

    private Series initSeries(User user) {
        Series series = Series.builder().title("seriesName").user(user).build();
        return seriesRepository.save(series);
    }
}