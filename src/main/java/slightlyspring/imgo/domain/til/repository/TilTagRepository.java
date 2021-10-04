package slightlyspring.imgo.domain.til.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slightlyspring.imgo.domain.til.domain.TilTag;

@Repository
public interface TilTagRepository extends JpaRepository<TilTag, Long> {

  List<TilTag> findByTilId(Long tilId);
  List<TilTag> findByTilIdIn(List<Long> tilIds);
}
