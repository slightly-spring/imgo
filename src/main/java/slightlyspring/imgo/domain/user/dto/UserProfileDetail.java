package slightlyspring.imgo.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import slightlyspring.imgo.domain.user.domain.Rival;
import slightlyspring.imgo.domain.user.domain.UserBadge;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserProfileDetail {
    private List<UserBadge> userBadges = new ArrayList<>();
    private List<Rival> rivals = new ArrayList<>();
    private List<UserTilRecord> userTilRecords = new ArrayList<>();
}
