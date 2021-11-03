package slightlyspring.imgo.domain.user.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class ProfilePageData {
    private UserProfile userProfile;
    private UserProfileDetail userProfileDetail;

    private String pageRole;
    private List<Integer> miniCalendar = new ArrayList<>();

    public ProfilePageData(UserProfile userProfile, UserProfileDetail userProfileDetail) {
        LocalDate now = LocalDate.now();
        this.userProfile = userProfile;
        this.userProfileDetail = userProfileDetail;
        for(int i=1; i<=7; i++) {
            miniCalendar.add(now.with(WeekFields.of(Locale.KOREA).dayOfWeek(), i).getDayOfMonth());
        }
    }
}
