package slightlyspring.imgo.domain.series.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserLikesSeries;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "series")
@Getter
@Builder
@DynamicInsert //Dynamic - null 값이 들어오면 쿼리에 미포함
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Series extends CreatedModifiedTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "series_id")
    private Long id;

    @Builder.Default //db 에 제대로 들어가는지 테스트
    private String title = "미지정";

    private String description; // 한 줄 소개

    @ColumnDefault("FALSE")
    private boolean completed;

    @ColumnDefault("0")
    private int likeCount;

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
