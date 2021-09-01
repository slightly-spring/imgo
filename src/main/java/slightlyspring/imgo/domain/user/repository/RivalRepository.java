package slightlyspring.imgo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import slightlyspring.imgo.domain.user.domain.Rival;

import java.util.List;

public interface RivalRepository extends JpaRepository<Rival, Long> {
    List<String> findAllByUserId(@Param("user_id") String userId);
}