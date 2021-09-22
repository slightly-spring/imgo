package slightlyspring.imgo.domain.til.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserLikesTil;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tils")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Til extends CreatedModifiedTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "til_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType = SourceType.COMMON;

    private String source;

    @ColumnDefault("0")
    private int likeCount;

    @ColumnDefault("0")
    private int viewCount;

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
