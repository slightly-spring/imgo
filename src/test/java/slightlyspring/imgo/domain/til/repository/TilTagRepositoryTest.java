package slightlyspring.imgo.domain.til.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilTag;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@SpringBootTest
class TilTagRepositoryTest {

  @Autowired
  private TilTagRepository tilTagRepository;
  @Autowired
  private TilRepository tilRepository;
  @Autowired
  private TagRepository tagRepository;
  @Test
  void save() {
    //given
    Til til = tilRepository.save(Til.builder().title("til이름").build());
    Tag tag = tagRepository.save(Tag.builder().name("태그이름").build());
    TilTag tilTag = TilTag.builder().til(til).tag(tag).build();

    //when
    TilTag savedTilTag = tilTagRepository.save(tilTag);

    //then
    assertThat(savedTilTag).isEqualTo(tilTag);
  }
}