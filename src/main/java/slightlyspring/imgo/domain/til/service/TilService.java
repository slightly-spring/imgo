package slightlyspring.imgo.domain.til.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilImage;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilImageRepository;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.repository.UserTilRecordRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TilService {

    private final TilRepository tilRepository;
    private final TilTagRepository tilTagRepository;
    private final TilImageRepository tilImageRepository;
    private final UserRepository userRepository;
    private final UserTilRecordRepository userTilRecordRepository;

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Long save(Til til, List<Tag> tags) {
        Til savedTil = tilRepository.save(til);

        saveImagesFromContent(savedTil, savedTil.getContent());

        List<TilTag> tilTags = new ArrayList<>();
        for(Tag tag : tags) {
            TilTag tilTag = TilTag.builder()
                    .tag(tag)
                    .til(til)
                    .build();
            tilTags.add(tilTag);
        }
        tilTagRepository.saveAll(tilTags);

        User user = til.getUser();
        user.updateLastWriteAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        int characterCount = til.getContent().length();
        Optional<UserTilRecord> userTilRecord = userTilRecordRepository.findUserTilRecordByUserAndBaseDate(user, LocalDate.now());
        if (userTilRecord.isEmpty()) {
            UserTilRecord newUserTilRecord = UserTilRecord.builder()
                    .tilCount(1)
                    .characterCount(characterCount)
                    .user(savedUser)
                    .build();
            userTilRecordRepository.save(newUserTilRecord);
        } else {
            userTilRecordRepository.save(userTilRecord.get().update(characterCount));
        }

        return savedTil.getId();
    }

    private List<TilImage> saveImagesFromContent(Til til, String content) {
        tilImageRepository.deleteByTil(til);
        tilImageRepository.flush();
        List<String> urls = getImageUrlsFromContent(content);
        List<TilImage> tilImages = urls.stream().map(url->TilImage.builder().url(url).til(til).build()).collect(Collectors.toList());
        return tilImageRepository.saveAll(tilImages);
    }

    private List<String> getImageUrlsFromContent(String content) {
        List<String> images = new ArrayList<>();

        Pattern pattern = Pattern.compile("<img[^>]*src=[\\\"']?([^>\\\"']+)[\\\"']?[^>]*>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            images.add(matcher.group(1));
        }

        return images;
    }

    public List<Til> findByUserId(Long userId) {
        return tilRepository.findByUserId(userId);
    }

}
