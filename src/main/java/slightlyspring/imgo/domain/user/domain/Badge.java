package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

@Entity
@Table(name = "badges")
@Getter @Setter
public class Badge extends CreatedTimeOnlyEntity {
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private int id;

    private int type_id;

    private int level;

    private String name;

    private String description;

    private String logo;

    @OneToMany(mappedBy = "badge")
    private List<UserBadge> userBadges = new ArrayList<>();
}
