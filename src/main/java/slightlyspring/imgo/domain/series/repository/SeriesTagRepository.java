package slightlyspring.imgo.domain.series.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.series.domain.SeriesTag;

public interface SeriesTagRepository extends JpaRepository<SeriesTag, Long> {

  List<SeriesTag> findBySeriesIdIn(List<Long> seriesIds);

}
