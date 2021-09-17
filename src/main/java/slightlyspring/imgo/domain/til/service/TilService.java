package slightlyspring.imgo.domain.til.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.repository.TilRepository;
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

    public Long save(Til til) {
        Long tilId = tilRepository.save(til).getId();

        User user = til.getUser();
        user.updateLastWriteAt(LocalDateTime.now());
        userRepository.save(user);

        int characterCount = til.getContent().length();
        Optional<UserTilRecord> userTilRecord = userTilRecordRepository.findUserTilRecordByUserAndBaseDate(user, LocalDate.now());
        if (userTilRecord.isEmpty()) {
            UserTilRecord newUserTilRecord = UserTilRecord.builder()
                    .characterCount(characterCount)
                    .user(user)
                    .build();
            userTilRecordRepository.save(newUserTilRecord);
        } else {
            userTilRecordRepository.save(userTilRecord.get().update(characterCount));
        }

        return tilId;
    }

}
