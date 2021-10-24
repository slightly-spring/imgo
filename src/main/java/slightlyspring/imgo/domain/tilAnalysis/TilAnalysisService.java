package slightlyspring.imgo.domain.tilAnalysis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TilAnalysisService {

  private final UserRepository userRepository;
  private final TilRepository tilRepository;
  private final SeriesRepository seriesRepository;
  private final TilTagRepository tilTagRepository;

  public TilAnalysisData getTilAnalysisDataByUserId(Long userId) {
    TilAnalysisData returnData = new TilAnalysisData();
    returnData.setUser(userRepository.getById(userId));

    // intro
    returnData.setNumTilPast30Days(getIntroNumTilPast30DaysByUserId(userId));
    returnData.setNumSeriesPast30Days(getIntroNumSeriesPast30DaysByUserId(userId));

    // Top5 Tag 별, 전체에 대한 나의 사용 비율
    List<Pair<Tag, Integer>> top5TagToRateTilList = getTop5TagToRateTilSortedListByUserId(userId);
    returnData.setTagToRateTilSortedList(top5TagToRateTilList);

    // Tag 사용빈도
    List<Pair<Tag, Integer>> tagToRateSortedList = getTagToRateSortedListByUserId(userId);
    returnData.setTagToRateSortedList(tagToRateSortedList);
    returnData.setTagTop3ByRate(
        tagToRateSortedList.subList(0, Math.min(tagToRateSortedList.size(), 3)).stream()
            .map(p -> p.getFirst())
            .collect(Collectors.toList()));

//    setContinuousDays(tilAnalysisData);
//    setBadges(tilAnalysisData);

    return returnData;
  }

  // --- PUBLIC SET ANALYSIS DATA ---
  public Long getIntroNumTilPast30DaysByUserId(Long userId) {
    LocalDateTime from = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIN);
    return tilRepository.countByUserIdAndCreatedDateAfter(userId, from);
  }

  public Long getIntroNumSeriesPast30DaysByUserId(Long userId) {
    LocalDateTime from = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIN);
    return seriesRepository.countByUserIdAndCreatedDateAfter(userId, from);
  }

  public List<Pair<Tag, Integer>> getTop5TagToRateTilSortedListByUserId(Long userId) {
    List<Entry<Tag, Integer>> top5TagsSortedByUserId = getTop5TagToNumTilSortedListByUserId(userId);
    Map<Tag, Integer> top5TagsAllByUserId = new HashMap<>();
    for (Entry<Tag, Integer> e : top5TagsSortedByUserId) {
      top5TagsAllByUserId.put(e.getKey(), getNumTilByTagId(e.getKey().getId()));
    }

    List<Pair<Tag, Integer>> tagToRateTilSortedList = new ArrayList<>();
    for (Entry<Tag, Integer> e : top5TagsSortedByUserId) {
      Tag userKey = e.getKey();
      Integer userVal = e.getValue();
      int rateTil = (int) ((userVal.doubleValue() / top5TagsAllByUserId.get(userKey).doubleValue())*100);
      tagToRateTilSortedList.add(Pair.of(userKey, rateTil));
    }
    return tagToRateTilSortedList;
  }

  public void setContinuousDays(TilAnalysisData tilAnalysisData) {

  }

  public List<Pair<Tag,Integer>> getTagToRateSortedListByUserId(Long userId) {
    Map<Tag, Integer> tagToNumTilMapByUserId= getTagToNumTilMapByUserId(userId);
    List<Entry<Tag, Integer>> tagToNumTilSortedListByValue = tagToNumTilMapToSortedListByValue(tagToNumTilMapByUserId);

    Long sum = 0l;
    for (Entry<Tag, Integer> e : tagToNumTilSortedListByValue) {
      sum += e.getValue().longValue();
    }

    List<Pair<Tag, Integer>> tagToRateSortedList = new ArrayList<>();
    for (Entry<Tag, Integer> e : tagToNumTilSortedListByValue) {
      int tmpRate = (int) ((e.getValue().doubleValue() / sum.doubleValue()) * 100);
      tagToRateSortedList.add(Pair.of(e.getKey(), tmpRate));
    }

    return tagToRateSortedList;
  }

  public void setBadges(TilAnalysisData tilAnalysisData) {

  }

  // --- Helper ---
  private Map<Tag, Integer> getTagToNumTilMapByUserId(Long userId) {
    List<Long> tilIds = tilRepository.findByUserId(userId)
        .stream().map(t -> t.getId()).collect(Collectors.toList());

    List<TilTag> tilTags = tilTagRepository.findByTilIdIn(tilIds);

    Map<Tag, Integer> tagToNumTilMap = new HashMap<>();
    for (TilTag tt : tilTags) {
      Tag tag = tt.getTag();
      Integer currentNumOfTilsByTag = 0;
      if (tagToNumTilMap.containsKey(tag)) {
        currentNumOfTilsByTag = tagToNumTilMap.get(tag);
      }
      tagToNumTilMap.put(tag, currentNumOfTilsByTag + 1);
    }

    return tagToNumTilMap;
  }
  private List<Entry<Tag, Integer>> getTop5TagToNumTilSortedListByUserId(Long userId) {
    Map<Tag, Integer> tagToNumTilMapByUserId = getTagToNumTilMapByUserId(userId);
    List<Entry<Tag, Integer>> reListSorted = tagToNumTilMapToSortedListByValue(tagToNumTilMapByUserId);
    List<Entry<Tag, Integer>> tagsTop5Sorted = new ArrayList<>(
        reListSorted.subList(0, Math.min(reListSorted.size(), 5)));

    return tagsTop5Sorted;
  }

  private Integer getNumTilByTagId(Long tagId) {
    return tilTagRepository.countByTagId(tagId).intValue();
  }

  private List<Entry<Tag, Integer>> tagToNumTilMapToSortedListByValue(Map<Tag, Integer> tagToNumTilMap) {
    List<Entry<Tag, Integer>> reList = new ArrayList<>(tagToNumTilMap.entrySet());
    reList.sort(Entry.comparingByValue(Comparator.reverseOrder()));
    return reList;
  }
}
