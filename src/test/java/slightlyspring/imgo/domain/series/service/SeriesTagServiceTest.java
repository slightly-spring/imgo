package slightlyspring.imgo.domain.series.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.domain.SeriesTag;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.series.repository.SeriesTagRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class SeriesTagServiceTest {

  @InjectMocks
  private SeriesTagService seriesTagService;

  @Mock
  private SeriesTagRepository seriesTagRepository;


  @DisplayName("Tag가_하나인_series_카드를_가져온다")
  @Test
  void getTagsMapBySeriesIds() {
    //given
    Series series = Series.builder().title("시리즈이름").build();
    Tag tag = Tag.builder().name("태그이름").build();
    List<SeriesTag> seriesTags = createSeriesTag(series, Arrays.asList(tag));

    Long fakeId = 1l;
    ReflectionTestUtils.setField(series, "id", fakeId);
    ReflectionTestUtils.setField(tag, "id", fakeId);
    ReflectionTestUtils.setField(seriesTags.get(0), "id", fakeId);

    //mocking
    given(seriesTagRepository.findBySeriesIdIn(any()))
        .willReturn(Arrays.asList(seriesTags.get(0)));

    //when
    Map<Long, List<Tag>> tmp = seriesTagService.getTagsMapBySeriesIds(
        Arrays.asList(series.getId()));

    //then
    assertThat(tmp.get(series.getId())).isEqualTo(Arrays.asList(tag));

  }

  @DisplayName("여러_Tag를_가지고있는_series_카드를_가져온다")
  @Test
  void getTagsMapBySeriesIds_DuplicatedKey() {
    //given
    Series series = Series.builder().build();
    Tag tagA = Tag.builder().name("tagA").build();
    Tag tagB = Tag.builder().name("tagB").build();

    List<SeriesTag> seriesTags = createSeriesTag(series, Arrays.asList(tagA, tagB));

    ReflectionTestUtils.setField(series,"id", 1l);


    //mocking
    given(seriesTagRepository.findBySeriesIdIn(any()))
        .willReturn(seriesTags);

    //when
    Map<Long, List<Tag>> tagsMapBySeriesIds = seriesTagService.getTagsMapBySeriesIds(
        Arrays.asList(series.getId()));

    //then
    assertThat(tagsMapBySeriesIds.get(series.getId())).hasSize(2);
    assertThat(tagsMapBySeriesIds.get(series.getId())).isEqualTo(Arrays.asList(tagA, tagB));

  }

  private List<SeriesTag> createSeriesTag(Series series, List<Tag> tags) {
    List<SeriesTag> re = new ArrayList<>();
    for (Tag t : tags) {
      SeriesTag tmp = SeriesTag.builder().series(series).tag(t).build();
      re.add(tmp);
    }
    return re;
  }
}