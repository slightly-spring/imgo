package slightlyspring.imgo.domain.til.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class TilCardService {

  private final TilService tilService;
  private final UserService userService;
  private final TilTagService tilTagService;


  public List<TilCardData> getTilCardDataListByUserId(Long userId) {
    List<TilCardData> tilCardDataList = new ArrayList<>();

    List<Til> tils = tilService.findByUserId(userId);
    User user = userService.findById(userId);

    for (Til til : tils) {
      List<Tag> tags = tilTagService.getTagsById(til.getId());
      TilCardData tmp = TilCardData.builder()
          .title(til.getTitle())
          .likeCount(til.getLikeCount())
          .createdAt(til.getCreatedDate())
          .tags(Stream.ofNullable(tags).map(Object::toString).collect(Collectors.toList()))
          .nickname(user.getNickname())
          .build();
      tilCardDataList.add(tmp);
    }

    return tilCardDataList;
  }



}
