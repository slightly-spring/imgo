package slightlyspring.imgo.user.domain;

import lombok.Getter;
import lombok.Setter;
import slightlyspring.imgo.til.domain.Til;
import slightlyspring.imgo.til.domain.TilComment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String profileImg;

    private String profileDescription;

    private int nowContinuousDays;

    private int maxContinuousDays;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @OneToOne(mappedBy = "user")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "user")
    private List<UserTilRecord> userTilRecords = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserNotice> userNotices = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserBadge> userBadges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserLikesSeries> userLikesSeries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserLikesTil> userLikesTils = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TilComment> tilComments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Til> tils = new ArrayList<>();
}
