package slightlyspring.imgo.domain.series.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import slightlyspring.imgo.domain.series.domain.Series;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findAllByUserId(@Param("user_id") Long userId);

    List<Series> findAllByUserIdAndTitleContains(@Param("user_id") Long userId, @Param("title") String title);
    Boolean existsByUserIdAndTitle(@Param("user_id") Long userId, @Param("title") String title);

    List<Series> findByUserId(Long userId, Pageable pageable);
    Page<Series> findAll(Pageable pageable);

    Long countByUserIdAndCreatedDateAfter(Long userId, LocalDateTime from);

}
