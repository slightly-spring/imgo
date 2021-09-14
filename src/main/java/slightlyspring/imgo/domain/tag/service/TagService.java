package slightlyspring.imgo.domain.tag.service;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.tag.domain.Tag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class TagService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Tag> searchTags(String word) {
        SearchResult<Tag> result = Search.session(entityManager)
                .search(Tag.class)
                .where(f -> f.match()
                        .fields("name")
                        .matching(word))
                .fetch(10);
        System.out.println("result = " + result);
        return result.hits();
    }

}
