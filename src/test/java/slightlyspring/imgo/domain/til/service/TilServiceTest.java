package slightlyspring.imgo.domain.til.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.til.domain.SourceType;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TilServiceTest {

    @Autowired
    TilRepository tilRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeriesRepository seriesRepository;

    @Test
    void TIL_게시물_작성() {
        // given
        User user = User.builder()
                .nickname("user01")
                .build();
        User savedUser = userRepository.save(user);

        Series series = Series.builder()
                .title("series title")
                .build();
        Series savedSeries = seriesRepository.save(series);

        Til til = Til.builder()
                .title("til title")
                .content("til content test")
                .sourceType(SourceType.COMMON)
                .source("스프링 데이터 JPA")
                .user(savedUser)
                .series(savedSeries)
                .build();

        // when
        Til savedTil = tilRepository.save(til);

        // then
        assertThat(savedTil).isEqualTo(til);
    }
}