package slightlyspring.imgo.domain.til.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.repository.UserTilRecordRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TilService {

    private final TilRepository tilRepository;
    private final UserRepository userRepository;
    private final UserTilRecordRepository userTilRecordRepository;
    private final TilTagRepository tilTagRepository;

    @Transactional
    public Long save(Til til) {
        Long tilId = tilRepository.save(til).getId();

        User user = til.getUser();
        user.updateLastWriteAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        int characterCount = til.getContent().length();
        Optional<UserTilRecord> userTilRecord = userTilRecordRepository.getUserTilRecordByUserAndBaseDate(user, LocalDate.now());
        if (userTilRecord.isEmpty()) {
            UserTilRecord newUserTilRecord = UserTilRecord.builder()
                    .characterCount(characterCount)
                    .user(savedUser)
                    .build();
            userTilRecordRepository.save(newUserTilRecord);
        } else {
            userTilRecordRepository.save(userTilRecord.get().update(characterCount));
        }

        return tilId;
    }

    public List<Til> findByUserId(Long userId) {
        return tilRepository.findByUserId(userId);
    }

}
