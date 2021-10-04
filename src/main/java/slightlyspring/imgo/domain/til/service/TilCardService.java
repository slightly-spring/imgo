package slightlyspring.imgo.domain.til.service;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class TilCardService {

  private final UserService userService;
  private final TilTagService tilTagService;

  private final TilRepository tilRepository;

  public List<TilCardData> getTilCardDataPageByUserId(Pageable pageable ,Long userId) {
    List<TilCardData> tilCardDataList = new ArrayList<>();

    List<Til> tilPages = tilRepository.findByUserId(userId, pageable);
//    User user = userService.findById(userId);

    List<Long> tilIds = tilPages.stream().map(t -> t.getId()).collect(Collectors.toList());
    Map<Long, List<Tag>> tagMapByTilIds = tilTagService.getTagsMapByTilIds(tilIds);

    for (Til til : tilPages) {
      Long tilId = til.getId();
      List<Tag> tags = tagMapByTilIds.get(tilId);

      TilCardData tmp = TilCardData.builder()
          .title(til.getTitle())
          .likeCount(til.getLikeCount())
          .createdAt(til.getCreatedDate())
          .tags(Stream.ofNullable(tags).map(Object::toString).collect(Collectors.toList()))
//          .nickname(user.getNickname())
          .nickname(til.getUser().getNickname())
          .build();
      tilCardDataList.add(tmp);
    }

    return tilCardDataList;
  }



}
