package slightlyspring.imgo.domain.rival.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.rival.domain.Rival;

import java.util.List;

public interface RivalRepository extends JpaRepository<Rival, Long> {
    List<Rival> findByUserId(Long userId);
    void deleteByUserIdAndTargetId(Long userId, Long targetId);

    Long countByUserId(Long userId);

    Long countByTargetId(Long targetId);
}