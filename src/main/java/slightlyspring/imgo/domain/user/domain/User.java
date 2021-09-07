package slightlyspring.imgo.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilComment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@Builder
@AllArgsConstructor
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String profileImg;

    private String profileDescription;

    private int nowContinuousDays;

    private int maxContinuousDays;

    private LocalDateTime lastWriteAt;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserTilRecord> userTilRecords = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserNotice> userNotices = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserBadge> userBadges = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserLikesSeries> userLikesSeries = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserLikesTil> userLikesTils = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TilComment> tilComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Til> tils = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Rival> rivals = new ArrayList<>();

    public User() {

    }
}
