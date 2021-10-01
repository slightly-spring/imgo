package slightlyspring.imgo.domain.til.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserLikesTil;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert //Dynamic - null 값이 들어오면 쿼리에 미포함
@DynamicUpdate
@Table(name = "tils")
@Entity
public class Til extends CreatedModifiedTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "til_id")
    private Long id;

    @Builder.Default
    private String title = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); //db에 잘 들어가는지 확인

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ColumnDefault("'COMMON'")
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

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
