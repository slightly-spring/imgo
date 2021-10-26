package slightlyspring.imgo.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserProfile {

    private Long id;

    private String nickname;
    private String profileImg;
    private String profileDescription;

    private int tilCount;
    private int seriesCount;
}
