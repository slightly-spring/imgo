package slightlyspring.imgo.domain.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.user.domain.UserBadge;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

  List<UserBadge> findByUserIdAndBadgeIdIn(Long userId, List<Long> badgeIds);
}
