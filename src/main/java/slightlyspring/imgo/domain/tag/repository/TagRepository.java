package slightlyspring.imgo.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);
}
