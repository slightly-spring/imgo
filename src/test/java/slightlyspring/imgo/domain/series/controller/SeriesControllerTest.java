package slightlyspring.imgo.domain.series.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.series.service.SeriesService;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SeriesControllerTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesService seriesService;

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
}