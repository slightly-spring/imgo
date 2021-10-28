package slightlyspring.imgo.domain.badge;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.badge.domain.Badge;
import slightlyspring.imgo.domain.badge.domain.BadgeLevel;
import slightlyspring.imgo.domain.badge.domain.BadgeType;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
  Badge getByBadgeTypeAndLevel(BadgeType badgeType, BadgeLevel lv1);
  List<Badge> getByBadgeType(BadgeType badgeType);
}
