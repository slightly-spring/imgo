package slightlyspring.imgo.user.domain;

import lombok.Getter;
import slightlyspring.imgo.badge.domain.Badge;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
