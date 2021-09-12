package slightlyspring.imgo.domain.til.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserLikesTil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tils")
@NoArgsConstructor
@Getter
@Builder
public class Til {
    @Id
    @GeneratedValue
    @Column(name = "til_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    private String source;

    @ColumnDefault("0")
    private int likeCount;

    @ColumnDefault("0")
    private int viewCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "series_id")
    private Series series;

    @OneToMany(mappedBy = "til")
    private List<TilComment> tilComments = new ArrayList<>();

    @OneToMany(mappedBy = "til")
    private List<UserLikesTil> userLikesTils = new ArrayList<>();

    @OneToMany(mappedBy = "til")
    private List<TilTag> tilTags = new ArrayList<>();
}
