package slightlyspring.imgo.domain.til.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
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

  // TilId - Tags 맵을 리턴
  public Map<Long, List<Tag>> getTagsMapByTilIds(List<Long> tilIds) {
//    List<TilTag> tilTags = tilTagRepository.findByTilId(tilId);
    List<TilTag> tilTags = tilTagRepository.findByTilIdIn(tilIds);

    Map<Long, List<Tag>> re = new HashMap<>();
    for (TilTag tt : tilTags) {
      Til til = tt.getTil();
      Tag tag = tt.getTag();
      if (re.containsKey(til.getId())) {
        re.get(til.getId()).add(tag);
      } else {
        List<Tag> tmp = new ArrayList<>(Arrays.asList(tag));
        re.put(til.getId(), tmp);
      }
    }

    return re;
  }
}
