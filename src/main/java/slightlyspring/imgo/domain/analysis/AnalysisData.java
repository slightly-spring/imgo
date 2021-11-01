package slightlyspring.imgo.domain.analysis;

import java.util.EnumMap;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.util.Pair;
import slightlyspring.imgo.domain.badge.domain.BadgeLevel;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.badge.domain.Badge;
import slightlyspring.imgo.domain.user.domain.User;

@Data
@Builder
public class AnalysisData {

  // owner
  User user;
  // intro
  private Long numTilPast30Days;
  private Long numSeriesPast30Days;
  //캘린더
  // ...
  // Top5 Tag 별, 전체에 대한 나의 사용 비율
  private List<Pair<Tag,Integer>> tagToRateTilSortedList;
  // Til 작성 지속 기간
  private int nowContinuousDays;
  private int maxContinuousDays;
  // Tag 사용빈도
  private List<Pair<Tag,Integer>> tagToRateSortedList;
  private List<Tag> tagTop3ByRate;
  // 얻은 뱃지
//  private EnumMap<BadgeType, EnumMap<BadgeLevel, Badge>> ownedBadgeMap;
  private List<String> validBadgeTypes;
}
