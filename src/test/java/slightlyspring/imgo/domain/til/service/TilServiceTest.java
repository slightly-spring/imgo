package slightlyspring.imgo.domain.til.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.til.domain.SourceType;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.repository.UserTilRecordRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TilServiceTest {

    @Autowired
    TilRepository tilRepository;

    @Autowired
    TilService tilService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeriesRepository seriesRepository;

    @Autowired
    UserTilRecordRepository userTilRecordRepository;

    @Test
    @Transactional
    void TIL_게시물_오늘_첫_작성() {
        // given
        User member = User.builder()
                .nickname("member01")
                .build();
        User savedUser = userRepository.save(member);

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
        Long tilId = tilService.save(til);

        // then
        Til savedTil = tilRepository.findById(tilId).orElse(null);

        // FIXME 조회하는 것부터 문제 발생.
//        UserTilRecord userTilRecord = userTilRecordRepository.getUserTilRecordByUserAndBaseDate(savedTil.getUser(), LocalDate.now()).orElse(null);
//        System.out.println("userTilRecord = " + userTilRecord);
//
        assertThat(savedTil).isEqualTo(til);
//        assertThat(userTilRecord.getTilCount()).isEqualTo(1);
//        assertThat(userTilRecord.getCharacterCount()).isEqualTo(til.getContent().length());
    }
}