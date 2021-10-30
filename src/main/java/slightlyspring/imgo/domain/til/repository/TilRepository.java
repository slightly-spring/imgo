package slightlyspring.imgo.domain.til.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.til.domain.Til;

public interface TilRepository extends JpaRepository<Til, Long> {

  int countAllByUserId(Long userId);
  List<Til> findByUserId(Long userId);
  List<Til> findByUserId(Long userId, Pageable pageable);
  List<Til> findBySeries(Series series);
  List<Til> findByUserIdAndSeriesId(Long userId, Long seriesId, Pageable pageable);
  Page<Til> findAll(Pageable pageable);
  Long countByUserIdAndCreatedDateAfter(Long userId, LocalDateTime from);
}
