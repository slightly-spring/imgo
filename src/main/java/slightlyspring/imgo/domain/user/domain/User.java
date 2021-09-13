package slightlyspring.imgo.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilComment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends CreatedModifiedTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String profileImg;

    private String profileDescription;

    private int nowContinuousDays;

    private int maxContinuousDays;

    private LocalDateTime lastWriteAt; // Til 에서 가져와야 되나?

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

}
