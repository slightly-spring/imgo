package slightlyspring.imgo.domain.tag.domain;

import lombok.Getter;
import slightlyspring.imgo.domain.series.domain.SeriesTag;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
public class Tag extends CreatedTimeOnlyEntity {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag")
    private List<TilTag> tilTags = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<SeriesTag> seriesTags = new ArrayList<>();
}
