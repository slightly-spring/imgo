package slightlyspring.imgo.domain.analysis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.badge.BadgeService;
import slightlyspring.imgo.domain.badge.domain.BadgeLevel;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.badge.domain.Badge;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserBadgeRepository;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TilAnalysisService {

  private final UserRepository userRepository;
  private final TilRepository tilRepository;
  private final SeriesRepository seriesRepository;
  private final TilTagRepository tilTagRepository;
  private final UserBadgeRepository userBadgeRepository;
  private final BadgeService badgeService;

  public TilAnalysisData getTilAnalysisDataByUserId(Long userId) {
    // intro
    Long numTilPast30Days = getIntroNumTilPast30DaysByUserId(userId);
    Long numSeriesPast30Days = getIntroNumSeriesPast30DaysByUserId(userId);

    // Top5 Tag 별, 전체에 대한 나의 사용 비율
    List<Pair<Tag, Integer>> top5TagToRateTilSortedList = getTop5TagToRateTilSortedListByUserId(
        userId);

    // 지속시간
    int nowContinuousDays = getNowContinuousDaysByUserId(userId);
    int maxContinuousDays = getMaxContinuousDaysByUserId(userId);

    // Tag 사용빈도
    List<Pair<Tag, Integer>> tagToRateSortedList = getTagToRateSortedListByUserId(userId);
    List<Tag> tagTop3ByRate = tagToRateSortedList.subList(0, Math.min(tagToRateSortedList.size(), 3))
        .stream()
        .map(Pair::getFirst)
        .collect(Collectors.toList());

    // 얻은 뱃지
    EnumMap<BadgeType, EnumMap<BadgeLevel, Badge>> badgeMapByUserId = getOwnedBadgeMapByUserId(userId);


//    setBadges(tilAnalysisData);

    return TilAnalysisData.builder()
        .user(userRepository.getById(userId))
        .numTilPast30Days(numTilPast30Days)
        .numSeriesPast30Days(numSeriesPast30Days)
        .tagToRateTilSortedList(top5TagToRateTilSortedList)
        .nowContinuousDays(nowContinuousDays)
        .maxContinuousDays(maxContinuousDays)
        .tagToRateSortedList(tagToRateSortedList)
        .tagTop3ByRate(tagTop3ByRate)
        .ownedBadgeMap(badgeMapByUserId)
        .build();
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

  public int getNowContinuousDaysByUserId(Long userId) {
    User user = userRepository.getById(userId);
    return user.getNowContinuousDays();
  }

  public int getMaxContinuousDaysByUserId(Long userId) {
    User user = userRepository.getById(userId);
    return user.getMaxContinuousDays();
  }


  public List<Pair<Tag,Integer>> getTagToRateSortedListByUserId(Long userId) {
    Map<Tag, Integer> tagToNumTilMapByUserId= getTagToNumTilMapByUserId(userId);
    List<Entry<Tag, Integer>> tagToNumTilSortedListByValue = tagToNumTilMapToSortedListByValue(tagToNumTilMapByUserId);

    long sum = 0L;
    for (Entry<Tag, Integer> e : tagToNumTilSortedListByValue) {
      sum += e.getValue().longValue();
    }

    List<Pair<Tag, Integer>> tagToRateSortedList = new ArrayList<>();
    for (Entry<Tag, Integer> e : tagToNumTilSortedListByValue) {
      int tmpRate = (int) ((e.getValue().doubleValue() / (double) sum) * 100);
      tagToRateSortedList.add(Pair.of(e.getKey(), tmpRate));
    }

    return tagToRateSortedList;
  }

  public EnumMap<BadgeType, EnumMap<BadgeLevel, Badge>> getOwnedBadgeMapByUserId(Long userId) {
    return badgeService.getBadgeMapByUserId(userId);
  }

  // --- Helper ---
  private Map<Tag, Integer> getTagToNumTilMapByUserId(Long userId) {
    List<Long> tilIds = tilRepository.findByUserId(userId)
        .stream().map(Til::getId).collect(Collectors.toList());

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
    return new ArrayList<>(
        reListSorted.subList(0, Math.min(reListSorted.size(), 5)));
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
