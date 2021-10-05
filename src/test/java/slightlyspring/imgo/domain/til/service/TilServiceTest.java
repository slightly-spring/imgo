package slightlyspring.imgo.domain.til.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.service.TagService;
import slightlyspring.imgo.domain.til.domain.SourceType;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilImage;
import slightlyspring.imgo.domain.til.repository.TilImageRepository;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.repository.UserTilRecordRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Autowired
    TagService tagService;

    @Autowired
    TilImageRepository tilImageRepository;

    @Test
    @Transactional
    @WithMockUser(username="user")
    void TIL_게시물_오늘_첫_작성() {
        // given
        User user = User.builder()
                .nickname("member01")
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

        List<String> tagList = Arrays.asList("tag1", "tag2");
        List<Tag> savedTags = tagService.saveTags(tagList);

        // when
        Long tilId = tilService.save(til, savedTags);

        // then
        Til savedTil = tilRepository.findById(tilId).orElse(null);
        UserTilRecord userTilRecord = userTilRecordRepository.findUserTilRecordByUserAndBaseDate(savedTil.getUser(), LocalDate.now()).orElse(null);

        assertThat(savedTil).isEqualTo(til);
        assertThat(userTilRecord.getTilCount()).isEqualTo(1);
        assertThat(userTilRecord.getCharacterCount()).isEqualTo(til.getContent().length());
    }

    @Test
    @Transactional
    @WithMockUser(username="user")
    void TIL_게시물_작성_시리즈태그제목없이() {
        // given
        User user = User.builder()
                .nickname("member01")
                .build();
        User savedUser = userRepository.save(user);

        Til til = Til.builder()
                .content("til content test")
                .sourceType(SourceType.COMMON)
                .source("스프링 데이터 JPA")
                .user(savedUser)
                .build();

        // when
        Long tilId = tilService.save(til, new ArrayList<>());

        // then
        Til savedTil = tilRepository.findById(tilId).orElse(null);
        assertThat(savedTil).isEqualTo(til);
    }

    @Test
    @Transactional
    @WithMockUser(username="user")
    void TIL_게시물_작성_사진첨부() {
        // given
        User user = User.builder()
                .nickname("member01")
                .build();
        User savedUser = userRepository.save(user);

        Series series = Series.builder()
                .title("series title")
                .build();
        Series savedSeries = seriesRepository.save(series);

        Til til = Til.builder()
                .title("til title")
                .content("<img src='abc'></img><img src='abcd'></img>")
                .sourceType(SourceType.COMMON)
                .source("스프링 데이터 JPA")
                .user(savedUser)
                .series(savedSeries)
                .build();

        // when
        Long tilId = tilService.save(til,  new ArrayList<>());

        // then
        Til savedTil = tilRepository.findById(tilId).orElse(null);
        List<TilImage> tilImages = tilImageRepository.findAllByTil(savedTil);
        assertThat(savedTil).isEqualTo(til);
        assertThat(tilImages.size()).isEqualTo(2);

    }
}