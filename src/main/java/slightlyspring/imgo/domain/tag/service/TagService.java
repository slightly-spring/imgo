package slightlyspring.imgo.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    @PersistenceContext
    private EntityManager entityManager;

    private final TagRepository tagRepository;

    public List<Tag> searchTags(String word) {
        SearchResult<Tag> result = Search.session(entityManager)
                .search(Tag.class)
                .where(f -> f.match()
                        .fields("name")
                        .matching(word))
                .fetch(10);
        return result.hits();
    }

    @Transactional
    public List<Tag> saveTags(List<String> tags) {
        List<Tag> tagList = new ArrayList<>();
        System.out.println("tagList = " + tagList);
        for (String tag : tags) {
            if(!tagRepository.existsByName(tag)) {
                tagList.add(Tag.builder().name(tag).build());
            }
        }
        return tagRepository.saveAll(tagList);
    }

}
