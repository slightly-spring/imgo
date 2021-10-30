package slightlyspring.imgo.domain.analysis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
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
  void setIntro_NumTil_test() {
    //given
    User user = userRepository.save(User.builder().nickname("userA").build());
    Series series = seriesRepository.save(Series.builder().user(user).build());
    Til til = tilRepository.save(Til.builder().user(user).series(series).build());

    //when
    Long numTilPast30Days = tilAnalysisService.getIntroNumTilPast30DaysByUserId(
        user.getId());

    //then

    assertThat(numTilPast30Days).isEqualTo(1l);
  }

  @Test
  void setIntro_NumSeries_test() {
    //given
    User user = userRepository.save(User.builder().nickname("userA").build());
    Series series = seriesRepository.save(Series.builder().user(user).build());
    Til til = tilRepository.save(Til.builder().user(user).series(series).build());

    //when
    Long numSeriesPast30Days = tilAnalysisService.getIntroNumSeriesPast30DaysByUserId(
        user.getId());

    //then
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
    List<Pair<Tag, Integer>> top5TagToRateTilSortedList = tilAnalysisService.getTop5TagToRateTilSortedListByUserId(
        userA.getId());

    //then
    assertThat(top5TagToRateTilSortedList.size()).isEqualTo(1);
    assertThat(top5TagToRateTilSortedList.get(0).getFirst().getId()).isEqualTo(tag.getId());
    assertThat(top5TagToRateTilSortedList.get(0).getSecond()).isEqualTo((int)((1D/3D)*100));
  }

  @Test
  void Tag_사용빈도() {
    //given
    int numOfTag = 10;
    User user = userRepository.save(User.builder().nickname("userName").build());
    List<Tag> tmpTags = new ArrayList<>();
    for (int i = 0; i < numOfTag; i++) {
      Tag tmpTag = tagRepository.save(Tag.builder().name("tag"+i).build());
      tmpTags.add(tmpTag);
      for (int j = 1; j < i + 2; j++) {
        Til tmpTil = tilRepository.save(Til.builder().user(user).build());
        tilTagRepository.save(TilTag.builder().til(tmpTil).tag(tmpTag).build());
      }
    }
    //when
    List<Pair<Tag, Integer>> tmp = tilAnalysisService.getTagToRateSortedListByUserId(
        user.getId());

    //then
    for (Pair<Tag, Integer> p : tmp) {
      System.out.println(String.format("{ p.tag , p.Rate } = {%s, %d}", p.getFirst(), p.getSecond()));
    }
    for (int i=0; i<numOfTag; i++) {
      Pair<Tag, Integer> p = tmp.get(numOfTag - i - 1);
      assertThat(p.getFirst().getId()).isEqualTo(tmpTags.get(i).getId());
    }


  }
}