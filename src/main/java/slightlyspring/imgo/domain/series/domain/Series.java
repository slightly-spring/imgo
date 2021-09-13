package slightlyspring.imgo.domain.series.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserLikesSeries;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "series")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Series {
    @Id
    @GeneratedValue
    @Column(name = "series_id")
    private Long id;

    private String title;

    private String description;

    @ColumnDefault("FALSE")
    private boolean is_completed;

    @ColumnDefault("0")
    private int like_count;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "series")
    private List<Til> tils = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<UserLikesSeries> userLikesSeries = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<SeriesTag> seriesTags = new ArrayList<>();
}
