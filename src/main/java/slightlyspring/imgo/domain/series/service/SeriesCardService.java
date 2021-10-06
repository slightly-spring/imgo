package slightlyspring.imgo.domain.series.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.dto.SeriesCardData;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.dto.TilCardData;

@RequiredArgsConstructor
@Service
public class SeriesCardService {

  private final SeriesRepository seriesRepository;
  private final SeriesTagService seriesTagService;

  public List<SeriesCardData> getSeriesCardDataByUserID(Pageable pageable, Long userId) {
    List<SeriesCardData> seriesCardDataList = new ArrayList<>();

    List<Series> seriesPages = seriesRepository.findByUserId(userId, pageable);
    List<Long> seriesIds = seriesPages.stream().map(s -> s.getId()).collect(Collectors.toList());

    Map<Long, List<Tag>> tagMapBySeriesIds = seriesTagService.getTagsMapBySeriesIds(seriesIds);

    for (Series series : seriesPages) {
      Long seriesId = series.getId();
      List<Tag> tags = tagMapBySeriesIds.get(seriesId);

      SeriesCardData tmp = SeriesCardData.builder()
          .title(series.getTitle())
          .description(series.getDescription())
//          .tags(Stream.ofNullable(tags).map(t -> t.toString()).collect(Collectors.toList())) //이렇게 하면 리스트 자체를 string 으로 만듦
          .tags(tagListToStream(tags).map(t -> t.toString()).collect(Collectors.toList()))
          .completed(series.isCompleted())
          .build();
      seriesCardDataList.add(tmp);
    }

    return seriesCardDataList;
  }

  public Stream<Tag> tagListToStream(List<Tag> collection) {
    return Optional.ofNullable(collection)
        .map(Collection::stream)
        .orElseGet(Stream::empty);
  }
}
