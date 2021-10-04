package slightlyspring.imgo.domain.til.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;

@ExtendWith(MockitoExtension.class)
class TilCardServiceTest {

  @InjectMocks
  private TilTagService tilTagService;

  @Mock
  private TilTagRepository tilTagRepository;
  @Mock
  private TilRepository tilRepository;
  @Mock
  private TagRepository tagRepository;

  @Test
  @DisplayName("TilId-Tags 맵 불러오기")
  public void getTagsMapByTilIds() {
    //given
    Til tilA = Til.builder().build();
    Tag tagA = Tag.builder().build();
    TilTag tilTag = createTilTagEntity(tilA, tagA);

    Long fakeTilTagId = 1l;
    ReflectionTestUtils.setField(tilA, "id", fakeTilTagId);
    ReflectionTestUtils.setField(tagA, "id", fakeTilTagId);
    ReflectionTestUtils.setField(tilTag, "id", fakeTilTagId);

    //mocking
    given(tilTagRepository.findByTilIdIn(Arrays.asList(fakeTilTagId)))
        .willReturn(Arrays.asList(tilTag));

    //when
    Map<Long, List<Tag>> tmp = tilTagService.getTagsMapByTilIds(
        Arrays.asList(tilA.getId()));

    //then
    assertThat(tmp.get(tilA.getId())).isEqualTo(Arrays.asList(tagA));
  }

  private TilTag createTilTagEntity(Til til, Tag tag) {
    return TilTag.builder()
        .tag(tag)
        .til(til)
        .build();
  }

}