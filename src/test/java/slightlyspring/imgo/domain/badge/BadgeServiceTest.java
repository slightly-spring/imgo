package slightlyspring.imgo.domain.badge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.test.util.ReflectionTestUtils;
import slightlyspring.imgo.domain.badge.domain.Badge;
import slightlyspring.imgo.domain.badge.domain.BadgeLevel;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.rival.repository.RivalRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserBadge;
import slightlyspring.imgo.domain.user.repository.UserBadgeRepository;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class BadgeServiceTest {

  @InjectMocks
  private static BadgeService badgeService;

  @Mock
  private BadgeRepository badgeRepository;
  @Mock
  private UserBadgeRepository userBadgeRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private RivalRepository rivalRepository;

  private static EnumMap<BadgeType, EnumMap<BadgeLevel, Badge>> badgeMap
      =  new EnumMap<>(BadgeType.class);

  @BeforeAll
  static void BadgeInit() {
    int fakeId = 0;
    for (BadgeType badgeType : BadgeType.values()) {
      EnumMap<BadgeLevel, Badge> ttt = new EnumMap<>(BadgeLevel.class);
      for (Pair<BadgeLevel, Integer> pair : badgeType.levelValues) {
        Badge tmp = Badge.builder()
            .badgeType(badgeType)
            .level(pair.getFirst())
            .name(badgeType.name)
//            .description()
//            .logo()
            .build();
        for (BadgeLevel bl : BadgeLevel.values()) {
          ReflectionTestUtils.setField(tmp, "id", fakeId++);
        }

        ttt.put(pair.getFirst(), tmp);
        badgeMap.put(badgeType, ttt);
      }
    }
  }


  @Test
  void badgeInfo를_가져온다() throws IOException {
    //given
    //when
    EnumMap<BadgeType, EnumMap<BadgeLevel, Integer>> badgeInfo = badgeService.getBadgeInfo();

    //then
    System.out.println("badgeInfo = " + badgeInfo);
    assertThat(1).isEqualTo(1);
  }

  @Test
  void update_Type2_최초획득() {
    //given
    User user = User.builder().nickname("userA").build();
    ReflectionTestUtils.setField(user, "id", 1L);

    EnumMap<BadgeLevel, Badge> levelBadgeWithType2 = badgeMap.get(BadgeType.TYPE2);

    UserBadge newUserBadge = UserBadge.builder()
        .user(userRepository.getById(1l))
        .badge(levelBadgeWithType2.get(BadgeLevel.LV1))
        .build();
    ReflectionTestUtils.setField(newUserBadge, "id", 1L);

    //mocking
    given(badgeRepository.getByBadgeType(BadgeType.TYPE2))
        .willReturn(new ArrayList<>(levelBadgeWithType2.values()));
    given(badgeRepository.getByBadgeTypeAndLevel(any(), any()))
        .willReturn(levelBadgeWithType2.get(BadgeLevel.LV1));
    given(userBadgeRepository.findByUserIdAndBadgeIdIn(any(), any()))
        .willReturn(new ArrayList<>());
    given(userRepository.getById(any()))
        .willReturn(user);
    given(userBadgeRepository.save(any()))
        .willReturn(newUserBadge);
    //when
    List<Badge> tmp = badgeService.updateToUserWithBadgeType(user.getId(), BadgeType.TYPE2);
    //then
    assertThat(tmp).hasSize(1);
    assertThat(tmp.get(0)).isEqualTo(levelBadgeWithType2.get(BadgeLevel.LV1));
  }


  @Test
  void update_Type2_두번째획득_기준미달() {
    //given
    User user = User.builder().nickname("userA").build();
    ReflectionTestUtils.setField(user, "id", 1L);
    //라이벌설정 해야댐
    EnumMap<BadgeLevel, Badge> levelBadgeWithType2 = badgeMap.get(BadgeType.TYPE2);

    UserBadge userBadgeLv1 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV1))
        .build();
    UserBadge userBadgeLv2 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV2))
        .build();
    ReflectionTestUtils.setField(userBadgeLv1, "id", 0L);
    ReflectionTestUtils.setField(userBadgeLv2, "id", 1L);

    //mocking
    given(badgeRepository.getByBadgeType(BadgeType.TYPE2))
        .willReturn(new ArrayList<>(levelBadgeWithType2.values()));
//    given(badgeRepository.getByBadgeTypeAndLevel(any(), any()))
//        .willReturn(levelBadgeWithType2.get(BadgeLevel.LV1));
    given(userBadgeRepository.findByUserIdAndBadgeIdIn(any(), any()))
        .willReturn(new ArrayList<>(List.of(userBadgeLv1)));
//    given(userRepository.getById(any()))
//        .willReturn(user);
//    given(userBadgeRepository.save(any()))
//        .willReturn(userBadgeLv2);
    given(rivalRepository.countByUserId(any()))
        .willReturn(0L);
    //when
    List<Badge> tmp = badgeService.updateToUserWithBadgeType(user.getId(), BadgeType.TYPE2);
    //then
    assertThat(tmp).hasSize(0);
//    assertThat(tmp.get(0)).isEqualTo(levelBadgeWithType2.get(new ArrayList<>()));
  }

  @Test
  void update_Type2_세번째획득_기준성공() {
    //given
    User user = User.builder().nickname("userA").build();
    ReflectionTestUtils.setField(user, "id", 1L);
    //라이벌설정 해야댐
    EnumMap<BadgeLevel, Badge> levelBadgeWithType2 = badgeMap.get(BadgeType.TYPE2);

    UserBadge userBadgeLv1 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV1))
        .build();
    UserBadge userBadgeLv2 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV2))
        .build();
    UserBadge userBadgeLv3 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV3))
        .build();
    ReflectionTestUtils.setField(userBadgeLv1, "id", 0L);
    ReflectionTestUtils.setField(userBadgeLv2, "id", 1L);
    ReflectionTestUtils.setField(userBadgeLv2, "id", 2L);

    //mocking
    given(badgeRepository.getByBadgeType(BadgeType.TYPE2))
        .willReturn(new ArrayList<>(levelBadgeWithType2.values()));
    given(userBadgeRepository.findByUserIdAndBadgeIdIn(any(), any()))
        .willReturn(new ArrayList<>(List.of(userBadgeLv1, userBadgeLv2)));
    given(userRepository.getById(any()))
        .willReturn(user);

    when(badgeRepository.getByBadgeTypeAndLevel(any(), eq(BadgeLevel.LV3)))
        .thenReturn(levelBadgeWithType2.get(BadgeLevel.LV3));
    when(userBadgeRepository.save(any()))
        .thenReturn(userBadgeLv3);

    given(rivalRepository.countByUserId(any()))
        .willReturn(5L);
    //when
    List<Badge> tmp = badgeService.updateToUserWithBadgeType(user.getId(), BadgeType.TYPE2);
    //then
    assertThat(tmp).hasSize(1);
    assertThat(tmp.get(0)).isEqualTo(levelBadgeWithType2.get(BadgeLevel.LV3));
  }

  @Test
  void update_Type2_두번째세번째동시획득_기준성공() {
    //given
    User user = User.builder().nickname("userA").build();
    ReflectionTestUtils.setField(user, "id", 1L);
    //라이벌설정 해야댐
    EnumMap<BadgeLevel, Badge> levelBadgeWithType2 = badgeMap.get(BadgeType.TYPE2);

    UserBadge userBadgeLv1 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV1))
        .build();
    UserBadge userBadgeLv2 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV2))
        .build();
    UserBadge userBadgeLv3 = UserBadge.builder()
        .user(user)
        .badge(levelBadgeWithType2.get(BadgeLevel.LV3))
        .build();
    ReflectionTestUtils.setField(userBadgeLv1, "id", 0L);
    ReflectionTestUtils.setField(userBadgeLv2, "id", 1L);
    ReflectionTestUtils.setField(userBadgeLv2, "id", 2L);

    //mocking
    given(badgeRepository.getByBadgeType(BadgeType.TYPE2))
        .willReturn(new ArrayList<>(levelBadgeWithType2.values()));
    given(userBadgeRepository.findByUserIdAndBadgeIdIn(any(), any()))
        .willReturn(new ArrayList<>(List.of(userBadgeLv1)));
    given(userRepository.getById(any()))
        .willReturn(user);

    when(badgeRepository.getByBadgeTypeAndLevel(any(), any()))
        .thenReturn(
            levelBadgeWithType2.get(BadgeLevel.LV2),
            levelBadgeWithType2.get(BadgeLevel.LV3));
    when(userBadgeRepository.save(any()))
        .thenReturn(
            userBadgeLv2,
            userBadgeLv3);

    given(rivalRepository.countByUserId(any()))
        .willReturn(5L);
    //when
    List<Badge> tmp = badgeService.updateToUserWithBadgeType(user.getId(), BadgeType.TYPE2);
    //then
    assertThat(tmp).hasSize(2);
    assertThat(tmp.get(0)).isEqualTo(levelBadgeWithType2.get(BadgeLevel.LV2));
    assertThat(tmp.get(1)).isEqualTo(levelBadgeWithType2.get(BadgeLevel.LV3));
  }
}