package slightlyspring.imgo.domain.rival;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import slightlyspring.imgo.domain.rival.Rival;

import java.util.List;

public interface RivalRepository extends JpaRepository<Rival, Long> {
    List<String> findAllByUserId(@Param("user_id") String userId);
    List<Rival> findByUserId(Long userId);
    boolean existsByUserIdAndTargetId(Long userId, Long targetId);
}