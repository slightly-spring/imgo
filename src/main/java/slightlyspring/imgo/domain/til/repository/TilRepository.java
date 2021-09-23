package slightlyspring.imgo.domain.til.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.til.domain.Til;

public interface TilRepository extends JpaRepository<Til, Long> {

  public List<Til> findByUserId(Long userId);
}
