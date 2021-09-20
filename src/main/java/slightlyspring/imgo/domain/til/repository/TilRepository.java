package slightlyspring.imgo.domain.til.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.til.domain.Til;

public interface TilRepository extends JpaRepository<Til, Long> {
}
