package slightlyspring.imgo.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
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
}
