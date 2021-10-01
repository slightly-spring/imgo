package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

@Entity
@Table(name = "badges")
@Getter @Setter
@DynamicInsert //Dynamic - null 값이 들어오면 쿼리에 미포함
@DynamicUpdate
public class Badge extends CreatedTimeOnlyEntity {
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BadgeType type_id; //db 에 잘 저장되는지 확인하기

    //type 별 level 이 있음
    private int level;

    @Column(nullable = false)
    private String name;

    private String description;

    private String logo;

    @OneToMany(mappedBy = "badge")
    private List<UserBadge> userBadges = new ArrayList<>();
}
