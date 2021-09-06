package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_badges")
@Getter
public class UserBadge {
    @Id
    @GeneratedValue
    @Column(name = "user_badge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
