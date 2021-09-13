package slightlyspring.imgo.domain.series.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.series.domain.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
