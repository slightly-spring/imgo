package slightlyspring.imgo.domain.til.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.til.domain.Til;

public interface TilRepository extends JpaRepository<Til, Long> {

  int countAllByUserId(Long userId);
  List<Til> findByUserId(Long userId);
  List<Til> findByUserIdOrderByIdDesc(Long userId, Pageable pageable);
  Page<Til> findAll(Pageable pageable);

  Long countByUserIdAndCreatedDateAfter(Long userId, LocalDateTime from);
}
