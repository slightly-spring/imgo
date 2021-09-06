package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;
import slightlyspring.imgo.domain.series.domain.Series;

import javax.persistence.*;

@Entity
@Table(name = "user_likes_series")
@Getter
public class UserLikesSeries {
    @Id
    @GeneratedValue
    @Column(name = "user_likes_series_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;
}
