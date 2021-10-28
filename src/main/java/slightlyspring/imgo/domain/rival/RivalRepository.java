package slightlyspring.imgo.domain.rival;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RivalRepository extends JpaRepository<Rival, Long> {
    List<Rival> findByUserId(Long userId);
    void deleteByUserIdAndTargetId(Long userId, Long targetId);

    Long countByUserId(Long userId);

    Long countByTargetId(Long targetId);
}