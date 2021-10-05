package slightlyspring.imgo.domain.series.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slightlyspring.imgo.domain.series.domain.SeriesTag;

@Repository
public interface SeriesTagRepository extends JpaRepository<Long, SeriesTag> {

  List<SeriesTag> findBySeriesIdIn(List<Long> seriesIds);
}
