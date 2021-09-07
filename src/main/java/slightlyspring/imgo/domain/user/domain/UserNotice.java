package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_notices")
@Getter
public class UserNotice {
    @Id
    @GeneratedValue
    @Column(name = "user_notice_id")
    private Long id;

    private String content;

    private boolean isRead;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
