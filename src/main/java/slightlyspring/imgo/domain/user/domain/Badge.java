package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;
import lombok.Setter;
import slightlyspring.imgo.domain.user.domain.UserBadge;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "badges")
@Getter @Setter
public class Badge {
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private Long id;

    private String name;

    private String description;

    private String logo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "badge")
    private List<UserBadge> userBadges = new ArrayList<>();
}
