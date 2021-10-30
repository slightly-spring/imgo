package slightlyspring.imgo.domain.rival.domain;

import lombok.*;

import javax.persistence.*;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

@Entity
@Table(name = "rivals")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Rival  extends CreatedTimeOnlyEntity {
    @Id
    @GeneratedValue
    @Column(name = "rival_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private User target;
}
