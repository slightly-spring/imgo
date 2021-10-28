package slightlyspring.imgo.domain.user.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.badge.BadgeService;
import slightlyspring.imgo.domain.badge.domain.BadgeType;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;
import slightlyspring.imgo.domain.user.dto.UserProfile;
import slightlyspring.imgo.domain.user.dto.UserProfileDetail;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.repository.UserTilRecordRepository;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserTilRecordRepository userTilRecordRepository;
    private final TilRepository tilRepository;
    private final BadgeService badgeService;
    private final ModelMapper modelMapper;

    public LocalDateTime updateLastWriteAt(Long userId, LocalDateTime time) {
        return userRepository.getById(userId).updateLastWriteAt(time).getLastWriteAt();
    }

    public int updateNowContinuousDaysBatch(Long userId) {
        User user = userRepository.getById(userId);
        LocalDate from = LocalDate.now().minusDays(29);
        LocalDate now = LocalDate.now();
        List<UserTilRecord> records = userTilRecordRepository.findAllByUserAndBaseDateBetweenOrderByBaseDateDesc(
            user, from, now);

        if (records.isEmpty() || records.get(0).getBaseDate().isBefore(now)) {
            return user.updateNowContinuousDays(0).getNowContinuousDays();
        }

        int newNowContinuousDays = 1;
        for (int i=1; i<records.size(); i++) {
            LocalDate previousDate = records.get(i-1).getBaseDate();
            LocalDate currentDate = records.get(i).getBaseDate();
            if (previousDate.minusDays(1).isEqual(currentDate)) {
                newNowContinuousDays += 1;
            } else {
                break;
            }
        }
        return user.updateNowContinuousDays(newNowContinuousDays).getNowContinuousDays();
    }

    public int updateMaxContinuousDaysBatch(Long userId) {
        User user = userRepository.getById(userId);
        LocalDate from = LocalDate.now().minusDays(29);
        LocalDate now = LocalDate.now();
        List<UserTilRecord> records = userTilRecordRepository.findAllByUserAndBaseDateBetweenOrderByBaseDateDesc(
            user, from, now);

        if (records.isEmpty()) {
            return user.updateMaxContinuousDays(0).getMaxContinuousDays();
        }

        int newMax = 1;
        int i = 0;
        int j = 1;
        while (true) {
            if (j == records.size()) {
                newMax = Math.max(j - i, newMax);
                break;
            }
            LocalDate previousDate = records.get(j-1).getBaseDate();
            LocalDate currentDate = records.get(j).getBaseDate();
            if (!previousDate.minusDays(1).isEqual(currentDate)) {
                newMax = Math.max(j - i, newMax);
                i = j;
                j = i+1;
                continue;
            }
            j += 1;
        }

        User updatedUser = user.updateMaxContinuousDays(newMax);
        badgeService.updateToUserWithBadgeType(updatedUser.getId(), BadgeType.TYPE4);
        return updatedUser.getMaxContinuousDays();
    }

    public Boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    public UserProfile getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return modelMapper.map(user, UserProfile.class);
    }

    public UserProfileDetail getUserProfileDetail(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        UserProfileDetail userProfileDetail = modelMapper.map(user, UserProfileDetail.class);

        List<UserTilRecord> userTilRecords = userTilRecordRepository.findAllByUserAndBaseDateBetween(user, LocalDate.now().with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1), LocalDate.now());
        userProfileDetail.setUserTilRecords(userTilRecords);

        return userProfileDetail;
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
