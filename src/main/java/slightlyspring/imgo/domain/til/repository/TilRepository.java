package slightlyspring.imgo.domain.til.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.til.domain.Til;

public interface TilRepository extends JpaRepository<Til, Long> {

  List<Til> findByUserId(Long userId);
  List<Til> findByUserId(Long userId, Pageable pageable);
}
