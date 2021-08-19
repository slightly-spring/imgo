package slightlyspring.imgo.domain.til.domain;

import lombok.Getter;
import slightlyspring.imgo.domain.user.domain.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "til_comments")
@Getter
public class TilComment {
    @Id
    @GeneratedValue
    @Column(name = "til_comment_id")
    private Long id;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "til_id")
    private Til til;
}
