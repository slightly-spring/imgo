package slightlyspring.imgo.domain.til.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilImage;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.repository.TilImageRepository;
import slightlyspring.imgo.domain.til.repository.TilRepository;

@Service
@RequiredArgsConstructor
public class TilCardService {

  private final TilTagService tilTagService;
  private final TilRepository tilRepository;
  private final TilImageRepository tilImageRepository;

  /**
   * 1. 해당 user 의 모든 til
   */
  public List<TilCardData> getTilCardDataByUserId(Pageable pageable ,Long userId) {
    List<TilCardData> tilCardDataList = new ArrayList<>();

    List<Til> tilPages = tilRepository.findByUserId(userId, pageable);

    return generateTilCardData(tilCardDataList, tilPages);
  }

  /**
   * 2. 해당 user, 해당 시리즈의 til
   */
  public List<TilCardData> getTilCardDataByUserIdAndSeriesId(Pageable pageable ,Long userId, Long seriesId) {
    List<TilCardData> tilCardDataList = new ArrayList<>();

    List<Til> tilPages = tilRepository.findByUserIdAndSeriesId(userId, seriesId, pageable);

    return generateTilCardData(tilCardDataList, tilPages);
  }

  /**
   * 3. 모든 사용자의 til
   */
  public List<TilCardData> getTilCardData(Pageable pageable) {
    List<TilCardData> tilCardDataList = new ArrayList<>();

    List<Til> tilPages = findAll(pageable);

    return generateTilCardData(tilCardDataList, tilPages);
  }

  public Stream<Tag> tagListToStream(List<Tag> collection) {
    return Optional.ofNullable(collection)
        .map(Collection::stream)
        .orElseGet(Stream::empty);
  }

  public List<Til> findAll(Pageable pageable){
    Page<Til> tilPage = tilRepository.findAll(pageable);
    return tilPage.getContent();
  }

  //--- private ---//
  private String getThumbnailFromTil(Til til) {
    return Optional.ofNullable(tilImageRepository.findTilImageByTilOrderById(til))
            .map(TilImage::getUrl)
            .orElse("");
  }

  private List<TilCardData> generateTilCardData(List<TilCardData> tilCardDataList, List<Til> tilPages) {
    List<Long> tilIds = tilPages.stream().map(Til::getId).collect(Collectors.toList());
    Map<Long, List<Tag>> tagMapByTilIds = tilTagService.getTagsMapByTilIds(tilIds);

    for (Til til : tilPages) {
      Long tilId = til.getId();
      List<Tag> tags = tagMapByTilIds.get(tilId);

      TilCardData tmp = TilCardData.builder()
          .tilId(tilId)
          .title(til.getTitle())
          .likeCount(til.getLikeCount())
          .createdAt(til.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
          .tags(tagListToStream(tags).map(Tag::toString).collect(Collectors.toList()))
          .nickname(til.getUser().getNickname())
          .tilImageUrl(getThumbnailFromTil(til))
          .build();
      tilCardDataList.add(tmp);
    }

    return tilCardDataList;
  }

}
