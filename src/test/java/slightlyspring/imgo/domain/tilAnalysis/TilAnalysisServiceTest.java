package slightlyspring.imgo.domain.tilAnalysis;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@SpringBootTest
class TilAnalysisServiceTest {

  @Autowired
  private  TilAnalysisService tilAnalysisService;
  @Autowired
  private  UserRepository userRepository;
  @Autowired
  private  TilRepository tilRepository;
  @Autowired
  private  SeriesRepository seriesRepository;
  @Autowired
  private TagRepository tagRepository;
  @Autowired
  private TilTagRepository tilTagRepository;
  
  @Test
  void setIntro_test() {
    //given
    User user = userRepository.save(User.builder().nickname("userA").build());
    Series series = seriesRepository.save(Series.builder().user(user).build());
    Til til = tilRepository.save(Til.builder().user(user).series(series).build());

    //when
    TilAnalysisData tilAnalysisData = tilAnalysisService.getTilAnalysisDataByUserId(
        user.getId());

    //then
    Long numTilPast30Days = tilAnalysisData.getNumTilPast30Days();
    Long numSeriesPast30Days = tilAnalysisData.getNumSeriesPast30Days();
    assertThat(numTilPast30Days).isEqualTo(1l);
    assertThat(numSeriesPast30Days).isEqualTo(1l);
  }

  //30일 전 til을 만들고싶다
  //til 생성일이 자동화되어있음
  //어떻게 수정하지?

  @Test
  void 내가사용한_태그가_전체중_얼마나차지하는가() {

    //given
    User userA = userRepository.save(User.builder().nickname("userA").build());
    User userB = userRepository.save(User.builder().nickname("userA").build());
    Series seriesA = seriesRepository.save(Series.builder().user(userA).build());
    Series seriesB = seriesRepository.save(Series.builder().user(userB).build());

    Tag tag = tagRepository.save(Tag.builder().name("tagName").build());

    Til tilA = tilRepository.save(Til.builder().user(userA).series(seriesA).build());
    Til tilB = tilRepository.save(Til.builder().user(userB).series(seriesB).build());
    Til tilB2 = tilRepository.save(Til.builder().user(userB).series(seriesB).build());

    TilTag tilTagA = tilTagRepository.save(TilTag.builder().til(tilA).tag(tag).build());
    TilTag tilTagB = tilTagRepository.save(TilTag.builder().til(tilB).tag(tag).build());
    TilTag tilTagB2 = tilTagRepository.save(TilTag.builder().til(tilB2).tag(tag).build());

    //when
    TilAnalysisData tmp = tilAnalysisService.getTilAnalysisDataByUserId(
        userA.getId());

    //then
    List<Pair<Tag, Integer>> rateAsTag = tmp.getRateAsTag();
    assertThat(rateAsTag.size()).isEqualTo(1);
    assertThat(rateAsTag.get(0).getFirst().getId()).isEqualTo(tag.getId());
    assertThat(rateAsTag.get(0).getSecond()).isEqualTo((int)((1D/3D)*100));
  }

}