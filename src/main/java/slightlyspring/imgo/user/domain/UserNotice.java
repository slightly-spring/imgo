package slightlyspring.imgo.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
