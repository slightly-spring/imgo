package slightlyspring.imgo.domain.badge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import slightlyspring.imgo.domain.user.domain.UserBadge;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

@Entity
@Table(name = "badges")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert //Dynamic - null 값이 들어오면 쿼리에 미포함
@DynamicUpdate
public class Badge extends CreatedTimeOnlyEntity implements Comparable<Badge>{
    @Id
    @GeneratedValue
    @Column(name = "badge_id")
    private int id; //왜 이것만 int

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BadgeType badgeType; //db 에 잘 저장되는지 확인하기

    //type 별 level 이 있음
    @Enumerated(EnumType.STRING)
    private BadgeLevel level;

    @Column(nullable = false)
    private String name;

    private String description;

    private String logo;

    @OneToMany(mappedBy = "badge")
    private List<UserBadge> userBadges = new ArrayList<>();

    // method
    @Override
    public int compareTo(Badge otherBadge) {
        return this.getLevel().compareTo(otherBadge.getLevel());
    }

    public boolean isLast() {
        return this.getBadgeType().levelValues.size() == this.getLevel().ordinal()+1;
    }

}
