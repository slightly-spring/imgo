package slightlyspring.imgo.til.domain;

import lombok.Getter;
import slightlyspring.imgo.series.domain.Series;
import slightlyspring.imgo.user.domain.User;
import slightlyspring.imgo.user.domain.UserLikesTil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tils")
@Getter
public class Til {
    @Id
    @GeneratedValue
    @Column(name = "til_id")
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    private String source;

    private int likeCount;

    private int viewCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @OneToMany(mappedBy = "til")
    private List<TilComment> tilComments = new ArrayList<>();

    @OneToMany(mappedBy = "til")
    private List<UserLikesTil> userLikesTils = new ArrayList<>();

    @OneToMany(mappedBy = "til")
    private List<TilTag> tilTags = new ArrayList<>();
}
