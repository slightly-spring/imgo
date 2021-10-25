package slightlyspring.imgo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserTilRecordRepository extends JpaRepository<UserTilRecord, Long> {
    List<UserTilRecord> findAllByUserAndBaseDateBetween(User user, LocalDate start, LocalDate end);
    List<UserTilRecord> findAllByUserAndBaseDateBetweenOrderByBaseDateDesc(User user, LocalDate start, LocalDate end);
    Optional<UserTilRecord> findUserTilRecordByUserAndBaseDate(User user, LocalDate date);

    UserTilRecord getByUserId(Long userId);
}
