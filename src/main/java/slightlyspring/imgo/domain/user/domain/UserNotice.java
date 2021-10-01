package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

@Entity
@Table(name = "user_notices")
@Getter
@DynamicInsert //Dynamic - null 값이 들어오면 쿼리에 미포함
@DynamicUpdate
public class UserNotice extends CreatedTimeOnlyEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_notice_id")
    private Long id;

    private String content;

    @ColumnDefault("FALSE")
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
