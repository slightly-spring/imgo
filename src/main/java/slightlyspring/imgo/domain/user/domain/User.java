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
/**
 * Entity User
 *
 * id Long
 * nickName String
 * picture String
 * nowContinuousDays int
 * maxContinuousDays int
 * lastWriteAt LocalDateTime함
 *
 *
 *
 * noCon, maxCont, lastWriteAt - 기본값 필요
 * 0, 0, LocalDateTime.MIN힘
 *
 * 참조 값들은 기본값 null
 */
public class User extends CreatedModifiedTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String nickname; //글자수 제한 둬야하나?

    private String profileImg;

    private String profileDescription;

    @Builder.Default
    private int nowContinuousDays = 0;

    @Builder.Default
    private int maxContinuousDays = 0;

    @Builder.Default
    private LocalDateTime lastWriteAt = LocalDateTime.of(0,1,1,0,0,0,0); // Til 에서 가져와야 되나?


    /*---연관관계 매핑---*/
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY) //양방향 매핑.. 왜? 굳이?
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

    /*---메서드---*/
    public User updateNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
    public User updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
        return this;
    }

}
