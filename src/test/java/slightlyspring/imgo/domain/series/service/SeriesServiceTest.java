package slightlyspring.imgo.domain.series.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SeriesServiceTest {

    @Autowired
    SeriesRepository seriesRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void 나의_시리즈_목록_출력() {
        // given
        User user = User.builder()
                .nickname("test")
                .build();
        User user2 = User.builder()
                .nickname("test2")
                .build();
        User savedUser = userRepository.save(user);
        User savedUser2 = userRepository.save(user2);

        Series series = Series.builder()
                .title("series01")
                .user(savedUser)
                .build();
        Series series2 = Series.builder()
                .title("series02")
                .user(savedUser2)
                .build();
        Series series3 = Series.builder()
                .title("series03")
                .user(savedUser)
                .build();
        seriesRepository.save(series);
        seriesRepository.save(series2);
        seriesRepository.save(series3);

        // when
        List<Series> savedSeriesList = seriesRepository.findAllByUserId(savedUser.getId());

        // then
        assertThat(savedSeriesList).containsExactly(series, series3);
    }

    @Test
    void 시리즈_등록() {
        // given
        User user = User.builder()
                .nickname("test")
                .build();
        User savedUser = userRepository.save(user);

        Series series = Series.builder()
                .title("series01")
                .user(savedUser)
                .build();

        // when
        Series savedSeries = seriesRepository.save(series);

        // then
        assertThat(savedSeries)
                .usingComparator(Comparator.comparing(Series::getTitle))
                .isEqualTo(series);
    }


}