package slightlyspring.imgo.domain.series.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.domain.SeriesTag;
import slightlyspring.imgo.domain.series.dto.SeriesCardData;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.user.domain.User;

@ExtendWith(MockitoExtension.class)
class SeriesCardServiceTest {

  @InjectMocks
  private SeriesCardService seriesCardService;

  @Mock
  private SeriesRepository seriesRepository;
  @Mock
  private SeriesTagService seriesTagService;

  @DisplayName("SeriesCard_정보를_불러온다")
  @Test
  public void getSeriesCardDataByUserID() {

    //given
    Long fakeId = 1l;

    User fakeUser = User.builder().build();
    ReflectionTestUtils.setField(fakeUser, "id", fakeId);

    Series series = Series.builder().user(fakeUser).build();
    Tag tag1 = Tag.builder().name("태그이름1").build();
    Tag tag2 = Tag.builder().name("태그이름2").build();
    SeriesTag seriesTag1 = SeriesTag.builder().series(series).tag(tag1).build();
    SeriesTag seriesTag2 = SeriesTag.builder().series(series).tag(tag2).build();
    ReflectionTestUtils.setField(seriesTag1,"id", fakeId);
    ReflectionTestUtils.setField(seriesTag2,"id", fakeId+1);

    //mocking
    given(seriesRepository.findByUserId(any(),any()))
        .willReturn(Arrays.asList(series));

    Map<Long, List<Tag>> seriesMap = new HashMap<>();
    List<Tag> tags = Arrays.asList(tag1, tag2);
    seriesMap.put(series.getId(), tags);

    given(seriesTagService.getTagsMapBySeriesIds(Arrays.asList(series.getId())))
        .willReturn(seriesMap);

    //when
    int page = 0;
    int size = 1;
    Pageable pageable = PageRequest.of(page, size,Sort.by("id"));
    List<SeriesCardData> seriesCardDataByUserID = seriesCardService.getSeriesCardDataByUserID(
        pageable, fakeUser.getId());
    
    //then
    assertThat(seriesCardDataByUserID).hasSize(1);
    assertThat(seriesCardDataByUserID.get(0).getTags()).isEqualTo(Arrays.asList(tag1.toString(), tag2.toString()));
  }

}