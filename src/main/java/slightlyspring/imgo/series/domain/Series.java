package slightlyspring.imgo.series.domain;

import lombok.Getter;
import slightlyspring.imgo.til.domain.Til;
import slightlyspring.imgo.user.domain.UserLikesSeries;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "series")
@Getter
public class Series {
    @Id
    @GeneratedValue
    @Column(name = "series_id")
    private Long id;

    private String title;

    private String description;

    private boolean is_completed;

    private int like_count;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @OneToMany(mappedBy = "series")
    private List<Til> tils = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<UserLikesSeries> userLikesSeries = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<SeriesTag> seriesTags = new ArrayList<>();
}
