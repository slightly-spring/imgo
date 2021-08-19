package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_til_records")
@Getter
public class UserTilRecord {
    @Id
    @GeneratedValue
    @Column(name = "user_til_records_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date baseDate;

    private int tilCount;

    private int characterCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
