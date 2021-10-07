package slightlyspring.imgo.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.dsl.SearchSortFactory;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                .where(f -> f.wildcard()
                        .field("name")
                        .matching("*"+word+"*"))
                .sort(SearchSortFactory::score)
                .fetch(10);
        return result.hits();
    }

    @Transactional
    public List<Tag> saveTags(List<String> tagNames) {
        List<Tag> existingTags = tagRepository.findTagsByNameIn(tagNames);
        List<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        List<String> newTagNames = tagNames.stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .collect(Collectors.toList());
        List<Tag> newTags = newTagNames.stream()
                .map(newTagName -> Tag.builder().name(newTagName).build())
                .collect(Collectors.toList());

        return tagRepository.saveAll(newTags);
    }

}
