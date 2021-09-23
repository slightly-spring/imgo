package slightlyspring.imgo.domain.til.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;

@Service
@RequiredArgsConstructor
public class TilTagService {

  private final TilTagRepository tilTagRepository;

  public List<Tag> getTagsById(Long tilId) {
      List<TilTag> tilTags = tilTagRepository.findByTilId(tilId);
      List<Tag> tags = new ArrayList<>();
      for (TilTag tt : tilTags) {
          tags.add(tt.getTag());
      }
      return tags;
  }
}
