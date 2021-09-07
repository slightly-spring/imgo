package slightlyspring.imgo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.user.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
