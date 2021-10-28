package slightlyspring.imgo.domain.rival;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.badge.BadgeService;
import slightlyspring.imgo.domain.badge.domain.Badge;
import slightlyspring.imgo.domain.badge.domain.BadgeLevel;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserBadge;
import slightlyspring.imgo.domain.user.repository.UserBadgeRepository;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@SpringBootTest
@Transactional
public class RivalServiceInterTest {

  @Autowired
  private RivalService rivalService;
  @Autowired
  private RivalRepository rivalRepository;
  @Autowired
  private BadgeService badgeService;
  @Autowired
  private UserBadgeRepository userBadgeRepository;
  @Autowired
  private UserRepository userRepository;


  @Test
  void 라이벌이_저장될때_Type2와3_뱃지가_저장된다_1개() {
    //given
    badgeService.makeBadgesBatch();

    int numOfRivals = 2;
    List<User> users = createUserEntities(numOfRivals*2);
    List<Rival> rivals = createRivalEntities(numOfRivals, users);
    //when
    Long savedRivalId = rivalService.save(rivals.get(0));
    Rival rival = rivalRepository.getById(savedRivalId);
    User user = rival.getUser();
    User target = rival.getTarget();
    List<Badge> userBadges = userBadgeRepository.getByUserId(user.getId()).stream().map(UserBadge::getBadge).collect(
        Collectors.toList());
    List<Badge> targetBadges = userBadgeRepository.getByUserId(target.getId()).stream().map(UserBadge::getBadge).collect(
        Collectors.toList());

    //then
    assertThat(userBadges).hasSize(1);
    assertThat(userBadges.get(0).getBadgeType()).isEqualTo(BadgeType.TYPE2);
    assertThat(userBadges.get(0).getLevel()).isEqualTo(BadgeLevel.LV1);
    assertThat(targetBadges).hasSize(1);
    assertThat(targetBadges.get(0).getBadgeType()).isEqualTo(BadgeType.TYPE3);
    assertThat(targetBadges.get(0).getLevel()).isEqualTo(BadgeLevel.LV1);

  }

  /**
   * users 의 앞 절반 = user
   * 뒤 절반 = target
   * @param users users.size == numOfEntity*2
   */
  public List<Rival> createRivalEntities(int numOfEntity, List<User> users) {
    List<Rival> rivals = new ArrayList<>();
    for (int i=0; i<numOfEntity; i++){
      rivals.add(
          Rival.builder().id((long) i).user(users.get(i)).target(users.get(i + numOfEntity)).build());
    }
    return rivals;
  }

  public List<User> createUserEntities(int numOfEntity) {
    List<User> users = new ArrayList<>();
    for (int i=0; i<numOfEntity; i++){
      users.add(User.builder().id((long) i).nickname("user" + i).build());
    }
    return userRepository.saveAll(users);
  }
}
