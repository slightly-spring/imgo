package slightlyspring.imgo.domain.badge;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.badge.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
