package slightlyspring.imgo.domain.badge;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.badge.domain.Badge;
import slightlyspring.imgo.domain.badge.domain.BadgeLevel;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.rival.repository.RivalRepository;
import slightlyspring.imgo.domain.user.domain.UserBadge;
import slightlyspring.imgo.domain.user.repository.UserBadgeRepository;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BadgeService {

  private final BadgeRepository badgeRepository;
  private final UserBadgeRepository userBadgeRepository;
  private final UserRepository userRepository;
  private final RivalRepository rivalRepository;

  public List<Badge> updateToUserWithBadgeType(Long userId, BadgeType badgeType) {
    List<Badge> re = new ArrayList<>();

    List<Badge> badges = badgeRepository.getByBadgeType(badgeType);
    /*(long)*/
    List<UserBadge> userBadgeList = userBadgeRepository.findByUserIdAndBadgeIdIn(
        userId,
        badges.stream().map(Badge::getId).collect(Collectors.toList())
    );
    if (userBadgeList.isEmpty()) {
      Badge newBadge = badgeRepository.getByBadgeTypeAndLevel(badgeType, BadgeLevel.LV1);
      UserBadge newUserBadge = UserBadge.builder()
          .user(userRepository.getById(userId))
          .badge(newBadge)
          .build();
      userBadgeList.add(userBadgeRepository.save(newUserBadge));
      re.add(userBadgeList.get(0).getBadge());
    }
    List<Badge> badgesSortedByLevel = userBadgeList.stream().map(UserBadge::getBadge).sorted()
        .collect(Collectors.toList());
    Badge currentHighestBadge = badgesSortedByLevel.get(badgesSortedByLevel.size() - 1);

    BadgeLevel currentLevel = currentHighestBadge.getLevel();
    List<BadgeLevel> toSavedLevel = new ArrayList<>();
    while (!currentHighestBadge.isLast()) {
      Integer nextValue = badgeType.getNextValueByLevel(currentLevel);
      Integer userOwnedValue = getUserOwnedValueByUserIdAndBadgeType(userId, badgeType);
      if (nextValue == null || userOwnedValue == null || nextValue>userOwnedValue) {
        break;
      }
      BadgeLevel nextLevel = currentLevel.next();
      toSavedLevel.add(nextLevel);
      currentLevel = nextLevel;
    }

    for (BadgeLevel badgeLevel : toSavedLevel) {
      UserBadge newUserBadge = UserBadge.builder()
          .user(userRepository.getById(userId))
          .badge(badgeRepository.getByBadgeTypeAndLevel(badgeType, badgeLevel))
          .build();
      re.add(userBadgeRepository.save(newUserBadge).getBadge());
    }

    return re;
  }

  private Integer getUserOwnedValueByUserIdAndBadgeType(Long userId, BadgeType badgeType) {
    switch (badgeType) {
      case TYPE1: //셀럽
      case TYPE5: //내일은 출판왕
        return Integer.MAX_VALUE; //좋아요 구현 안돼있음, 스크랩 구현 안돼있음
      case TYPE2: //너로 정했다
        return rivalRepository.countByUserId(userId).intValue();
      case TYPE3: //공공의 적
        return rivalRepository.countByTargetId(userId).intValue();
      case TYPE4: //작심3일
        return userRepository.getById(userId).getMaxContinuousDays();
      default:
        return null;
    }
  }
  public List<Badge> makeBadgesBatch() {
    List<Badge> badges = new ArrayList<>();

    for (BadgeType badgeType : BadgeType.values()) {
      for (Pair<BadgeLevel, Integer> pair : badgeType.levelValues) {
        Badge tmp = Badge.builder()
            .badgeType(badgeType)
            .level(pair.getFirst())
            .name(badgeType.badgeName)
//            .description()
            .logo(badgeType.logo)
            .build();
        badges.add(tmp);
      }
    }
    return badgeRepository.saveAll(badges);
  }

  public EnumMap<BadgeType, EnumMap<BadgeLevel, Integer>> getBadgeInfo() {
    EnumMap<BadgeType, EnumMap<BadgeLevel, Integer>> badgeInfo
        = new EnumMap<>(BadgeType.class);
    for (BadgeType badgeType : BadgeType.values()) {
      EnumMap<BadgeLevel, Integer> tmp = new EnumMap<>(BadgeLevel.class);
      for (Pair<BadgeLevel, Integer> pair : badgeType.levelValues) {
        tmp.put(pair.getFirst(), pair.getSecond());
      }
      badgeInfo.put(badgeType, tmp);
    }

    return badgeInfo;
  }

  public EnumMap<BadgeType, EnumMap<BadgeLevel, Badge>> getBadgeMapByUserId(Long userId) {
    List<Badge> badgesByUserId = userBadgeRepository.getByUserId(userId).stream().map(UserBadge::getBadge).collect(
        Collectors.toList());
    return generateBadgeMapFromBadgeList(badgesByUserId);
  }

  public EnumMap<BadgeType, EnumMap<BadgeLevel, Badge>> generateBadgeMapFromBadgeList(List<Badge> badgeList) {
    EnumMap<BadgeType, EnumMap<BadgeLevel, Badge>> badgeMap = new EnumMap<>(BadgeType.class);
    for (Badge b : badgeList) {
      BadgeType badgeType = b.getBadgeType();
      BadgeLevel level = b.getLevel();
      if (!badgeMap.containsKey(badgeType)){
        badgeMap.put(badgeType, new EnumMap<>(BadgeLevel.class));
      }
      badgeMap.get(badgeType).put(level, b);
    }
    return badgeMap;
  }

}
