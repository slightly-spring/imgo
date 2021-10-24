package slightlyspring.imgo.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
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

    private final TilRepository tilRepository;
    private final SeriesRepository seriesRepository;

    private final UserRepository userRepository;
    private final UserTilRecordRepository userTilRecordRepository;

    private final ModelMapper modelMapper;

    public Boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    public UserProfile getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        UserProfile userProfile = modelMapper.map(user, UserProfile.class);
        userProfile.setTilCount(tilRepository.countAllByUserId(userId));
        userProfile.setSeriesCount(seriesRepository.countAllByUserId(userId));
        return userProfile;
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
