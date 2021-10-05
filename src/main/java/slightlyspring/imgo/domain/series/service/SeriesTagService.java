package slightlyspring.imgo.domain.series.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
      if (re.containsKey(st.getSeries().getId())) {
        re.get(st.getSeries().getId()).add(st.getTag()); // 테스트 필요함
      } else {
        List<Tag> tmp = Arrays.asList(st.getTag());
        re.put(st.getSeries().getId(), tmp);
      }
    }

    return re;
  }
}
