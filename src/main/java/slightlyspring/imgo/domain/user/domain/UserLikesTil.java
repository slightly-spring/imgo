package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;
import slightlyspring.imgo.domain.til.domain.Til;

import javax.persistence.*;

@Entity
@Table(name = "user_likes_tils")
@Getter
public class UserLikesTil {
    @Id
    @GeneratedValue
    @Column(name = "user_likes_til_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "til_id")
    private Til til;
}
