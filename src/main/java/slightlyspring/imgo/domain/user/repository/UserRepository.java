package slightlyspring.imgo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slightlyspring.imgo.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
