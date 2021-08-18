package slightlyspring.imgo.user.domain;

import lombok.Getter;
import slightlyspring.imgo.series.domain.Series;

import javax.persistence.*;

@Entity
@Table(name = "user_likes_series")
@Getter
public class UserLikesSeries {
    @Id
    @GeneratedValue
    @Column(name = "user_likes_series_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
}
