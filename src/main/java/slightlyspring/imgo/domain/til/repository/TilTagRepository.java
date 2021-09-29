package slightlyspring.imgo.domain.til.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.til.domain.TilTag;

public interface TilTagRepository extends JpaRepository<TilTag, Long> {
}
