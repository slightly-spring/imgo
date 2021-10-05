package slightlyspring.imgo.domain.til.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilImage;

import java.util.List;

public interface TilImageRepository extends JpaRepository<TilImage, Long> {
    void deleteByTil(Til til);
    List<TilImage> findAllByTil(Til til);
}
