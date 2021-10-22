package slightlyspring.imgo.domain.tilAnalysis;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.data.util.Pair;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.user.domain.Badge;
import slightlyspring.imgo.domain.user.domain.User;

@Data
public class TilAnalysisData {

  // owner
  User user;
  // intro
  private Long numTilPast30Days;
  private Long numSeriesPast30Days;
  //캘린더
  // ...
  // Til 작성 순위
  private List<Pair<Tag,Integer>> rateAsTag;
  // Til 작성 지속 기간
  private Long nowContinuousDays;
  private Long maxContinuousDays;
  // 자주 사용한 Tag
  private Map<Tag, Integer> tagRateMap;
  private List<Tag> tagTop3ByRate;
  // 얻은 뱃지
  private List<Badge> ownedBadges;
}
