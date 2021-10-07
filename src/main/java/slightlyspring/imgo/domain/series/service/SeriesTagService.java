package slightlyspring.imgo.domain.series.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.domain.SeriesTag;
import slightlyspring.imgo.domain.series.repository.SeriesTagRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;

@RequiredArgsConstructor
@Service
public class SeriesTagService {

  private final SeriesTagRepository seriesTagRepository;

  public Map<Long, List<Tag>> getTagsMapBySeriesIds(List<Long> seriesIds) {
    List<SeriesTag> seriesTags = seriesTagRepository.findBySeriesIdIn(seriesIds);

    Map<Long, List<Tag>> re = new HashMap<>();
    for (SeriesTag st : seriesTags) {
      Series series = st.getSeries();
      Tag tag = st.getTag();
      if (re.containsKey(series.getId())) {
        List<Tag> tags = re.get(series.getId());
        tags.add(tag);

      } else {
//        List<Tag> tmpTags = Stream.of(tag).collect(Collectors.toList());
        List<Tag> tmpTags = new ArrayList<>(Arrays.asList(tag));
        re.put(series.getId(), tmpTags);
      }
    }

    return re;
  }
}
