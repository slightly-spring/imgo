package slightlyspring.imgo.domain.til.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.repository.TilRepository;

@Service
@RequiredArgsConstructor
public class TilCardService {

  private final TilTagService tilTagService;
  private final TilRepository tilRepository;

  public List<TilCardData> getTilCardDataByUserId(Pageable pageable ,Long userId) {
    List<TilCardData> tilCardDataList = new ArrayList<>();

    List<Til> tilPages = tilRepository.findByUserId(userId, pageable);

    List<Long> tilIds = tilPages.stream().map(t -> t.getId()).collect(Collectors.toList());
    Map<Long, List<Tag>> tagMapByTilIds = tilTagService.getTagsMapByTilIds(tilIds);

    for (Til til : tilPages) {
      Long tilId = til.getId();
      List<Tag> tags = tagMapByTilIds.get(tilId);

      TilCardData tmp = TilCardData.builder()
          .title(til.getTitle())
          .likeCount(til.getLikeCount())
          .createdAt(til.getCreatedDate())
//          .tags(Stream.ofNullable(tags).map(Object::toString).collect(Collectors.toList()))
          .tags(tagListToStream(tags).map(t -> t.toString()).collect(Collectors.toList()))
          .nickname(til.getUser().getNickname())
          .build();
      tilCardDataList.add(tmp);
    }

    return tilCardDataList;
  }

  public Stream<Tag> tagListToStream(List<Tag> collection) {
    return Optional.ofNullable(collection)
        .map(Collection::stream)
        .orElseGet(Stream::empty);
  }


}
