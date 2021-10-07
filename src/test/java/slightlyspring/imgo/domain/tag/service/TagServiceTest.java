package slightlyspring.imgo.domain.tag.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TagServiceTest {

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;

    @BeforeEach
    void setup() {
        Tag tag = Tag.builder()
                .name("Spring")
                .build();
        Tag tag2 = Tag.builder()
                .name("Spring Security")
                .build();
        Tag tag3 = Tag.builder()
                .name("spring")
                .build();
        Tag tag4 = Tag.builder()
                .name("Java")
                .build();
        Tag tag5 = Tag.builder()
                .name("Javascript")
                .build();

        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        tagList.add(tag2);
        tagList.add(tag3);
        tagList.add(tag4);
        tagList.add(tag5);

        tagRepository.saveAll(tagList);
    }

    // FIXME tag 저장 시 elasticsearch에 즉시 인덱싱 안되는 문제 있음. test 전체로 돌렸을 때만 정상 실행 확인.
    @Test
    void tag_검색() {
        // given

        // when
        List<Tag> hits = tagService.searchTags("spring");

        // then
        assertThat(hits.size()).isGreaterThan(0);
    }

    @Test
    void saveTags_새_태그_저장() {
        // given
        List<String> tagNames = Arrays.asList("TIL", "List");

        // when
        List<Tag> savedTags = tagService.saveTags(tagNames);

        // then
        assertThat(savedTags.size()).isEqualTo(2);
    }

    @Test
    void saveTags_중복포함_태그_저장() {
        // given
        List<String> tagNames = Arrays.asList("Spring", "OS");

        // when
        List<Tag> savedTags = tagService.saveTags(tagNames);

        // then
        assertThat(savedTags.size()).isEqualTo(1);
    }
}