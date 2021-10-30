package slightlyspring.imgo.domain.rival.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.badge.BadgeService;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.rival.repository.RivalRepository;
import slightlyspring.imgo.domain.rival.domain.Rival;
import slightlyspring.imgo.domain.user.domain.User;

@Service
@RequiredArgsConstructor
public class RivalService {

  private final RivalRepository rivalRepository;
  private final BadgeService badgeService;

  static int MAX_NUM = 5;

  public boolean isRivalByUserIdAndTargetId(Long userId, Long targetId) {
    List<Rival> rivals = rivalRepository.findByUserId(userId);
    for (Rival rival : rivals) {
      if (rival.getTarget().getId().equals(targetId)) {
        return true;
      }
    }
    return false;
  }

  @Transactional
  public Long saveRival(Rival rival) {
    Long userId = rival.getUser().getId();
    Long targetId = rival.getTarget().getId();
    //이미 등록되었는지 check 하고, 저장
    List<Rival> rivals = rivalRepository.findByUserId(
        userId);
    for (Rival r : rivals) {
      if (r.getTarget().getId().equals(targetId)) {
        // 이미 UserId 가 TargetId 를 라이벌로 지정하고 있을 경우
        return r.getId();
      }
    }

    if (rivals.size() == MAX_NUM) {
      return 0L; // 꽉 찼을 때
    }

    // 새롭게 추가될 때 type2,3 뱃지 업데이트 하기
    badgeService.updateToUserWithBadgeType(userId, BadgeType.TYPE2);
    badgeService.updateToUserWithBadgeType(targetId, BadgeType.TYPE3);

    return rivalRepository.save(rival).getId();
  }

  public List<User> getTargetsByUserId(Long userId) {
    return rivalRepository.findByUserId(userId).stream().map(Rival::getTarget).collect(Collectors.toList());
  }

  public void deleteRival(Long userId, Long targetId) {
    rivalRepository.deleteByUserIdAndTargetId(userId, targetId);
  }

}
