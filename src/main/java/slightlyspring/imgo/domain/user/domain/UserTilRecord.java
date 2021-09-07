package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_til_records")
@Getter
public class UserTilRecord {
    @Id
    @GeneratedValue
    @Column(name = "user_til_records_id")
    private Long id;

    private LocalDate baseDate;

    private int tilCount;

    private int characterCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
