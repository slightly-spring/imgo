package slightlyspring.imgo.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import slightlyspring.imgo.domain.rival.domain.Rival;
import slightlyspring.imgo.domain.user.domain.UserBadge;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserProfileDetail {
    private List<UserBadge> userBadges = new ArrayList<>();
    private List<Rival> rivals = new ArrayList<>();
    private List<UserTilRecord> userTilRecords = new ArrayList<>();

    public boolean isRivalActive(Rival rival) {
        LocalDateTime activeTime = LocalDateTime.now().plusDays(-1);
        Duration duration = Duration.between(activeTime, rival.getTarget().getLastWriteAt());
        return duration.getSeconds() >= 0;
    }

    public boolean isRecorded(int day) {
        return this.userTilRecords.stream().anyMatch(record -> record.getBaseDate().getDayOfMonth() == day);
    }
}
