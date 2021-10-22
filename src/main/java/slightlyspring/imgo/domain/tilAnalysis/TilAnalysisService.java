package slightlyspring.imgo.domain.tilAnalysis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.backend.elasticsearch.lowlevel.index.settings.impl.Analysis;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.til.service.TilTagService;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TilAnalysisService {

  private final UserRepository userRepository;
  private final TilRepository tilRepository;
  private final SeriesRepository seriesRepository;
  private final TilTagRepository tilTagRepository;

  public TilAnalysisData getTilAnalysisDataByUserId(Long userId) {
    TilAnalysisData tilAnalysisData = new TilAnalysisData();
    tilAnalysisData.setUser(userRepository.getById(userId));

    setIntro(tilAnalysisData);
    setTilRate(tilAnalysisData);
    setContinuousDays(tilAnalysisData);
    setTagRate(tilAnalysisData);
    setTagRate(tilAnalysisData);
    setBadges(tilAnalysisData);

    return tilAnalysisData;
  }

  // --- PRIVATE SET ANALYSIS DATA ---
  private void setIntro(TilAnalysisData tilAnalysisData) {

    User owner = tilAnalysisData.getUser();
    LocalDate fromDate = LocalDate.now().minusDays(30);
    LocalTime zeroTime = LocalTime.MIN;
    LocalDateTime fromDateTime = LocalDateTime.of(fromDate, zeroTime);

    Long numTil = tilRepository.countByUserIdAndCreatedDateAfter(
        owner.getId(), fromDateTime);
    Long numSeries = seriesRepository.countByUserIdAndCreatedDateAfter(owner.getId(), fromDateTime);

    tilAnalysisData.setNumTilPast30Days(numTil);
    tilAnalysisData.setNumSeriesPast30Days(numSeries);
  }

  private void setTilRate(TilAnalysisData tilAnalysisData) {
    User owner = tilAnalysisData.getUser();
    List<Entry<Tag, Integer>> top5TagsByUserId = getTop5TagsTilNumByUserId(owner.getId());
    Map<Tag, Integer> top5TagsAll = new HashMap<>();
    for (Entry<Tag, Integer> e : top5TagsByUserId) {
      top5TagsAll.put(e.getKey(), getTilNumByTagId(e.getKey().getId()));
    }

    List<Pair<Tag, Integer>> rankAsTag = new ArrayList<>();
    for (Entry<Tag, Integer> e : top5TagsByUserId) {
      Tag userKey = e.getKey();
      Integer userVal = e.getValue();
      int rate = (int) ((userVal.doubleValue() / top5TagsAll.get(userKey).doubleValue())*100);
      rankAsTag.add(Pair.of(userKey, rate));
    }

    tilAnalysisData.setRateAsTag(rankAsTag);
  }

  private void setContinuousDays(TilAnalysisData tilAnalysisData) {

  }

  private void setTagRate(TilAnalysisData tilAnalysisData) {

  }

  private void setBadges(TilAnalysisData tilAnalysisData) {

  }

  // --- Helper ---
  private List<Entry<Tag, Integer>> getTop5TagsTilNumByUserId(Long userId) {
    List<Long> tilIds = tilRepository.findByUserId(userId)
        .stream().map(t -> t.getId()).collect(Collectors.toList());

    List<TilTag> tilTags = tilTagRepository.findByTilIdIn(tilIds);

    Map<Tag, Integer> re = new HashMap<>();
    for (TilTag tt : tilTags) {
      Tag tag = tt.getTag();
      Integer currentNumOfTilsByTag = 0;
      if (re.containsKey(tag)) {
        currentNumOfTilsByTag = re.get(tag);
      }
      re.put(tag, currentNumOfTilsByTag + 1);
    }

    List<Entry<Tag, Integer>> reList = new ArrayList<>(re.entrySet());
    reList.sort(Entry.comparingByValue(Comparator.reverseOrder()));

    List<Entry<Tag, Integer>> tagTop5 = new ArrayList<>(
        reList.subList(0, Math.min(reList.size(), 5)));

    return tagTop5;
  }

  private Integer getTilNumByTagId(Long tagId) {
    return tilTagRepository.countByTagId(tagId).intValue();
  }
}
