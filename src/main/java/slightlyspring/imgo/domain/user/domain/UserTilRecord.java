package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

@Entity
@Table(name = "user_til_records")
@Getter
public class UserTilRecord extends CreatedModifiedTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_til_records_id")
    private Long id;

    private LocalDate baseDate;

    private int tilCount;

    private int characterCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
