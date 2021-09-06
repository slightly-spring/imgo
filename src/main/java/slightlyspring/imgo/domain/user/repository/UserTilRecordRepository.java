package slightlyspring.imgo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;

import java.time.LocalDate;
import java.util.List;

public interface UserTilRecordRepository extends JpaRepository<UserTilRecord, Long> {
    List<UserTilRecord> findAllByUserAndBaseDateBetween(User user, LocalDate start, LocalDate end);
}
