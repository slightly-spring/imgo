package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

@Entity
@Table(name = "user_notices")
@Getter
public class UserNotice extends CreatedTimeOnlyEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_notice_id")
    private Long id;

    private String content;

    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
