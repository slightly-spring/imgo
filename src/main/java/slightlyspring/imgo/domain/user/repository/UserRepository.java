package slightlyspring.imgo.domain.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slightlyspring.imgo.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByNickname(String nickname);
}
