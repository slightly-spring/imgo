package slightlyspring.imgo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.user.domain.UserBadge;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
}
