package slightlyspring.imgo.domain.user.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Data
public class ProfilePageData {
    private UserProfile userProfile;
    private UserProfileDetail userProfileDetail;

    private String pageRole;
    private int firstDayOfWeek = LocalDate.now()
            .with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1)
            .getDayOfMonth();

    public ProfilePageData(UserProfile userProfile, UserProfileDetail userProfileDetail) {
        this.userProfile = userProfile;
        this.userProfileDetail = userProfileDetail;
    }
}
